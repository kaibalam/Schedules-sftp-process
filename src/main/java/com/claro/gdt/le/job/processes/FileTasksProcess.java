package com.claro.gdt.le.job.processes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;


@Component
public class FileTasksProcess {
    private static final Logger LOG = LoggerFactory.getLogger(FileTasksProcess.class);

    public boolean BorradoArchivo(String Direccion) {
        LOG.info("remove method file name: {}",Direccion);
        boolean exitoso = false;
        try {
            File archivo = new File(Direccion);
            exitoso = archivo.delete();

            /*if (exitoso) {
                File archivoC = new File(Direccion);
                exitoso = archivoC.delete();

            }*/
            LOG.info("borrado exitoso status: {}",exitoso);
            return exitoso;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }




}
