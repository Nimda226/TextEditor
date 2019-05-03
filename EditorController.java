package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class EditorController {

    @FXML
    private TextArea areaText = new TextArea();

    private TextFile currentTextFile;

    public IOResult<TextFile> load(Path file) {
        try {
            List<String> lines = Files.readAllLines(file);
            return new IOResult<>(true, new TextFile(file, lines));
        } catch (IOException e) {
            e.printStackTrace();
            return new IOResult<>(false, null);
        }
    }

    @FXML
    private void onNewFile(){
        areaText.clear();
    }

    @FXML
    //Метод, который позволяет загружать в прилложение файлы
    private void onLoad() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            IOResult<TextFile> io = load(file.toPath());

            if (io.isOk() && io.hasData()) {
                currentTextFile = io.getData();

                areaText.clear();
                currentTextFile.getContent().forEach(line -> areaText.appendText(line + "\n"));
            } else {
                System.out.println("Failed");
            }
        }
    }

    @FXML
    //Метод, закрывающий приложение
    private void onClose() {
        System.exit(0);
    }

    @FXML
    //Метод, описывающий кнопку About, выводящую alert с текстом
    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Информация");
        alert.setContentText("Текстовый редактор для тестового задания");
        alert.show();
    }

    @FXML
    //Метод, описывающий кнопку Save
    private void onSaveIn() {
        //Создаем предупреждение
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Внимание!");
        alert.setHeaderText("Сохранение файла.");
        alert.setContentText("Перед сохранением, убедитесь, что файл не занят другим процессом!");
        alert.show();
        // Создаем диалоговое окно с помощью FileChooser.showSaveDialog, так же создаем фильтры для txt файлов
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showSaveDialog(null);
        if (file != null){
            SaveFile.saveFileIn(areaText.getText(), file);
        }
    }

    //Метод, сохраняющий изменения в уже существующем файле
     /*    @FXML
        private void onSave() {
        TextFile textFile = new TextFile(currentTextFile.getFile(), Arrays.asList(areaText.getText().split("\n")));
        SaveFile.save(textFile);
    } */
}
