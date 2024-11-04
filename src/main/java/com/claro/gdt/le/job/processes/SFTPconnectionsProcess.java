package com.claro.gdt.le.job.processes;


import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SFTPconnectionsProcess {
    private static final Logger LOG = LoggerFactory.getLogger(SFTPconnectionsProcess.class);


    public Map<String, String> sftpConnectDataMapper(Map<String, String> tags) {
        Map<String, String> connectData = new HashMap<>();
        String dirSGTM = null;
        String fecha = null;
        Boolean downArchive = false;
        String address = tags.get("GDT_DIR_SERVER2");
        connectData.put("address", address);
        String user = tags.get("GDT_USUARIO_SGTM");
        connectData.put("user", user);
        String password = tags.get("GDT_PASS_SGTM");
        connectData.put("password", password);
        String host = tags.get("GDT_IP_SGTM");
        connectData.put("host", host);
        String codClaro = tags.get("GDT_ELBP_RPATH");
        connectData.put("codClaro", codClaro);
        LOG.info("datos de conexion sftp cargados");
        //String localPath ="C:\\Users\\ricardo.valenzuela\\Documents\\SGTM"; //##Cambiar
        String localPath ="C:\\Curso\\assets\\Test";
        File directorio = new File(address);
        //File directorio = new File(localPath);

        if (!directorio.exists())
            directorio.mkdir();
        connectData.put("localDirectory",localPath);
        LOG.info("Ruta CLARO a depositar archivos descargados: " + directorio);
        LocalDateTime date = LocalDateTime.now();
        fecha = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        LOG.info("fecha " + fecha);
        dirSGTM = "/" + codClaro + "/" + fecha;
        connectData.put("dirSGTM", dirSGTM);
        String prefix = tags.get("GDT_PREFIX_NAME");
        connectData.put("prefix",prefix);
        LOG.info("direccion de sftp a buscar archivos para lista de excepciones" + dirSGTM);
        return connectData;
    }

    public List<String> dowloadFiles(Map<String, String> tags) throws JSchException{
        List<String> respons = new ArrayList<>();
        String hostname = tags.get("host");
        String username = tags.get("user");
        String password = tags.get("password");
        String localDirectory = tags.get("address");
        LocalDateTime datePath = LocalDateTime.now();
        String remoteDirectory = tags.get("dirSGTM");
        int sftpPort = 22;
        JSch jSch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelSftp c = null;
        try {
            session = jSch.getSession(username, hostname, sftpPort);
            LOG.info("Created session");
            session.setPassword(password);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("PreferredAuthentications", "password");
            session.setConfig(config);
            session.connect();
            LOG.info("Started session!");
            channel = session.openChannel("sftp");
            channel.connect();
            LOG.info("channel openned");
            c = (ChannelSftp) channel;
            c.cd(remoteDirectory);
            LOG.info("Move to path directory: {}", remoteDirectory);
            Vector<ChannelSftp.LsEntry> listFiles = c.ls(tags.get("prefix")+"*.gz");

            LOG.info("files list to read on remote directory");
            ChannelSftp finalC = c;
            Channel finalChannel = channel;
            listFiles.forEach(fl -> {
                LOG.info("starts readen filename {} ", fl.getFilename());
                if (fl.getFilename().startsWith("LE") && (fl.getFilename().endsWith(".zip") || fl.getFilename().endsWith(".gz"))) {
                    LOG.info("if starts with LE and finish with .gz download process begin");
                    try {
                            finalC.get(remoteDirectory + "/" + fl.getFilename(), localDirectory);
                            LOG.info("Downloaded file name: " + fl);
                            String fileNewName = fl.getFilename().replace(".gz", "");
                            LOG.info("new file name " + fileNewName);
                            respons.add(fl.getFilename());

                    } catch (SftpException e) {
                        e.printStackTrace();
                        LOG.error("Error processin downloading files: " + e);
                        finalChannel.disconnect();

                    }
                }
            });
            finalC.exit();
        } catch (Exception e) {
            if (e.getMessage().contains("No such file")){
                LOG.error("La carpeta o archivo solicitado no ha sido creado: {}",remoteDirectory.concat(": exception message: ".concat(e.getMessage())));
                channel.disconnect();
                session.disconnect();
                return respons;
            }
            e.printStackTrace();
            LOG.error("ERROR during connection {}", e);
            channel.disconnect();
            session.disconnect();
            return respons;
        }
        channel.disconnect();
        session.disconnect();
        return respons;
    }

}
