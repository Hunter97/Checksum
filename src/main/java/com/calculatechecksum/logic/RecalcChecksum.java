/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calculatechecksum.logic;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prwallace
 */
public class RecalcChecksum {
    private File m_phoenixPath = new File ( "./Phoenix.ini" );
    private File m_phoenixBakPath = new File( " ./Phoenix.bak" );

    /**
     *
     */
    public void ReCalcChecksum () {};


    public boolean hasFile( Path dirpath, Path filepath ) throws IOException {
        boolean hasFile = false;

        if( dirpath.toFile().isDirectory() ) {
            try( DirectoryStream<Path> dirStream = Files.newDirectoryStream( dirpath ) ) {
                for( Path p : dirStream ) {
                    if( p.equals(filepath) ) {
                        hasFile = true;
                        break;
                    }
                }
            }
        }

        return hasFile;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String primary = "Phoenix.ini";
        String backup = "Phoenix.bak";
        String root = ".\\";
        String partition = "D:\\Setups\\";

        Path rootDirPath = Paths.get( root );
        Path rootFilePath = Paths.get( root, primary );
        Path rootBakPath = Paths.get( root, backup );
        Path partitionDirPath = Paths.get( partition );
        Path partitionBakPath = Paths.get( partition, backup );

        File backupFile = null;
        File primaryFile = null;

        Desktop desktop;

        boolean noBackup = false;
        boolean noPrimary = false;
       
        System.out.println( "RecalculateChecksum start" );
        RecalcChecksum recalc = new RecalcChecksum();

        try {
            if( recalc.hasFile( rootDirPath, rootBakPath )) {
                backupFile = new File( rootBakPath.toString() );
            }
            else if( recalc.hasFile( partitionDirPath, partitionBakPath )) {
                backupFile = new File( partitionBakPath.toString() );
            }
            else {
                noBackup = true;
            }

            if( recalc.hasFile(rootDirPath, rootFilePath)) {
                primaryFile = new File( rootFilePath.toString() );
            }
            else {
                noPrimary = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(RecalcChecksum.class.getName()).log(Level.SEVERE, "RecalcChecksum.hasFile: ", ex);
        }
        
        if( !noPrimary && Desktop.isDesktopSupported() ) {
            try {
                desktop = Desktop.getDesktop();
                //desktop.open( primaryFile );
                desktop.edit( primaryFile );
                System.out.println( "RecalculateChecksum launched file" );
            } catch (IOException ex) {
                Logger.getLogger(RecalcChecksum.class.getName()).log(Level.SEVERE, "RecalcChecksum getDesktop().open", ex);
            }
        }
        
        System.out.println( "RecalculateChecksum end" );
    }
}
