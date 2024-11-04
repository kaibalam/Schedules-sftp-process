package com.claro.gdt.le.job.controllers.jobs;


import com.claro.gdt.le.job.models.SGTException;
import com.claro.gdt.le.job.models.responses.TagsValuesResponse;
import com.claro.gdt.le.job.processes.FileTasksProcess;
import com.claro.gdt.le.job.processes.SFTPconnectionsProcess;
import com.claro.gdt.le.job.services.IDictionaryDAO;
import com.claro.gdt.le.job.utils.UnzipFile;
import com.jcraft.jsch.JSchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ExceptionListJob {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionListJob.class);
    private int wasProcess;

    private boolean stop = false;
    @Autowired
    private IDictionaryDAO dictionaryService;

    @Scheduled(cron = "${cron.param}")
    public void mainProcess() throws SGTException, JSchException {

        LOG.info("inicio de tarea de lista de excepciones");
        List<TagsValuesResponse> tags = dictionaryService.getAllTags();
        FileTasksProcess tasksProcess = new FileTasksProcess();
        Map<String, String> mapTags = new HashMap<>();
        tags.forEach(t -> mapTags.put(t.getTag(), t.getValue()));
        //crear conexion y descargar archivos
        SFTPconnectionsProcess connSftp = new SFTPconnectionsProcess();
        Map<String,String> mapped = connSftp.sftpConnectDataMapper(mapTags);
        List<String> fileNames = connSftp.dowloadFiles(mapped); //##debuger
        List<String> toRemove = new ArrayList<>();
        //List<String> fileNames = new ArrayList<>(); //##create a list to debug
        //fileNames.add("LE_202303160300.txt.zip");  //##Adding a filename to debug
        LOG.info("files {}",fileNames);
        File localFile = new File(mapped.get("address"));
        if (!CollectionUtils.isEmpty(fileNames)) {
            fileNames.forEach(fl -> {
                try {
                    wasProcess = dictionaryService.getNewValidation(fl, "SLN");
                    LOG.info("Existe como ya procesado? {}", wasProcess);
                    if (wasProcess == 0) {
                        dictionaryService.saveNewFile(fl, "SLN");
                    } else {
                        LOG.info("Ya existe eliminarlo de la lista {}", fl);
                        if (fileNames.size() <= 1) {
                            LOG.info("Si tamaño lista es <= 1 no continuar {}", fileNames.size());
                            stop = true;
                        } else {
                            LOG.info("Eliminar {}", fl);
                            boolean deleted = tasksProcess.BorradoArchivo(localFile + "/" + fl);
                            //fileNames.remove(fl);
                            LOG.info("deleted= {}", deleted);
                            toRemove.add(fl);
                            LOG.info("remove List: {}", toRemove.size());
                        }
                    }
                } catch (SGTException e) {
                    e.printStackTrace();
                    LOG.info("Error validatin if was process {}", e.getMessage());
                }
            });
            fileNames.removeAll(toRemove);
        }
        //Barrer la lista para procesar los archivos
        if (!CollectionUtils.isEmpty(fileNames) && !stop) {
            LOG.info("archivo descargado iniciando processo de lectura");
            //File localFile = new File(mapped.get("address")); //##CAmbiar
            //test localFile
            //File localFile = new File(mapped.get("localDirectory"));
            LOG.info("local directory loaded");
            List<String> localFiles = Arrays.stream(localFile.list()).collect(Collectors.toList());
            LOG.info("files listed");

            localFiles.forEach(ar -> {
                File inFile = new File(localFile + "/" + ar);
                LOG.info("starting files iteration " + ar);
                try {
                    UnzipFile unzipFile = new UnzipFile();
                    boolean isUnzipped = false;
                    if (ar.endsWith(".gz")) {
                        isUnzipped = unzipFile.unziping(inFile);
                        LOG.info("file unziped gz");
                    } else if (ar.endsWith(".zip")) {
                        isUnzipped = unzipFile.unzipWin(inFile);
                        LOG.info("file unziped zip");
                    }
                    if (isUnzipped) {
                        LOG.info("before enter ProcessLE");
                        //String fileNew = ar.replace(".zip","");
                         //##Cambiar
                        //fileNames.forEach(fln -> {
                            String fileNew = ar.replace(".gz", "");
                            try {
                                //dictionaryService.mainTask(mapTags, mapped.get("localDirectory") + "/" + fileNew);
                                dictionaryService.mainTask(mapTags, mapped.get("address") + "/" + fileNew);
                            } catch (SGTException | RuntimeException | FileNotFoundException | JSchException e) {
                                e.printStackTrace();
                                LOG.info("error al cargar el archivo {}",e.getMessage());
                            }
                       // });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error(e.getMessage());
                }
                String textFile = ar.replace(".gz","");

                boolean deleted = tasksProcess.BorradoArchivo(localFile + "/" + ar);
                boolean delText = tasksProcess.BorradoArchivo(localFile + "/" + textFile);
            });

        } else {
            LOG.info("There are no exception list files, nothing to process");
        }
        LOG.info("Fin del proceso");
    }
}
