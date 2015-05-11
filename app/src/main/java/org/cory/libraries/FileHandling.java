package org.cory.libraries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class FileHandling
{
    public static void cleanFolder(File folder, long days)
    {
        if(folder.exists())
        {
            File[] fileList = folder.listFiles();

            long eligibleForDeletion = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000L);

            for(File currentFile : fileList)
            {
                if(currentFile.lastModified() < eligibleForDeletion)
                {
                    if(!currentFile.delete())
                        System.out.println("Unable to delete " + currentFile.getName());
                }
            }
        }
    }

    public static void unzip(File zipFile) throws IOException
    {
        byte[] buffer = new byte[1024];

        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
        //get the zipped file list entry
        ZipEntry ze = zis.getNextEntry();
        String fileDirectory = zipFile.getParent();

        while(ze!=null)
        {
            String fileName = ze.getName();
            File newFile = new File(fileDirectory + File.separator + fileName);

            new File(newFile.getParent()).mkdirs();

            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while((len = zis.read(buffer)) > 0)
            {
                fos.write(buffer, 0, len);
            }

            fos.close();
            ze = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();
    }

    /**
     * @param src
     * @param dest
     * @throws java.io.IOException
     */
    public static void copyFile(File src, File dest) throws IOException
    {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dest);

        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        in.close();
        out.close();
    }
}
