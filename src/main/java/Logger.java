import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private boolean active = false;

    final int numEntries;
    int currentEntry;
    int notifyAfterEvery = 1000;

    String fileName;
    BufferedWriter writer;

    public Logger(String fileName, int numEntries) {
        this.fileName = fileName;
        this.numEntries = numEntries;

        try {
            writer = new BufferedWriter(new FileWriter("C:\\Users\\Mateusz\\Desktop\\1d_obj_recognition\\"+fileName+".txt", false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Logger notifyAfterEvery(int num){
        notifyAfterEvery = num;
        return this;
    }

    public Logger setActive(boolean value){
        active = value;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public void notifyAboutEntriesNumber(){
        System.out.println(String.format("%.2f%% of entries logged (%d)", ((float)currentEntry/numEntries)*100f, currentEntry));
    }

    public void addNewEntry(String entry){
        if(currentEntry < numEntries && active) {
            try {
                writer.append(entry);
                currentEntry++;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(currentEntry%notifyAfterEvery==0){
                notifyAboutEntriesNumber();
            }

            if(currentEntry==numEntries){
                System.out.println("Finished collecting data");
                active=false;
                close();
            }
        }


    }

    public void clearFile(){
        if(active) {
            try {
                writer.write("");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
