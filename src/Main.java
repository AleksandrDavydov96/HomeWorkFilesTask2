import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        GameProgress progress1 = new GameProgress(100, 3,2, 1110);
        GameProgress progress2 = new GameProgress(84, 5, 5, 2487);
        GameProgress progress3 = new GameProgress(70, 9,8, 3567);

        String fileSave1Path = "C:/GamesFilesHomeWork/savegames/save1.txt";
        String fileSave2Path = "C:/GamesFilesHomeWork/savegames/save2.txt";
        String fileSave3Path = "C:/GamesFilesHomeWork/savegames/save3.txt";

        saveGame(progress1, fileSave1Path);
        saveGame(progress2, fileSave2Path);
        saveGame(progress3, fileSave3Path);

        List<String> filesListToZip = new ArrayList<>();
        filesListToZip.add(fileSave1Path);
        filesListToZip.add(fileSave2Path);
        filesListToZip.add(fileSave3Path);

        zipFiles("C:/GamesFilesHomeWork/savegames/saves.zip", filesListToZip);

    }
    public static void saveGame(GameProgress gameProgress, String filePath) throws FileNotFoundException {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void zipFiles(String archiveFilePath, List<String> filesList) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archiveFilePath))) {
            for (String file : filesList) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    File nameFale = new File(file);
                    ZipEntry entry = new ZipEntry(nameFale.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
        for (String deleteFile : filesList) {
            File fileForDelete = new File(deleteFile);
            fileForDelete.delete();
        }
    }
}