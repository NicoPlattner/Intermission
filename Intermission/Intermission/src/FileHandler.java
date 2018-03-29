import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileHandler {

    private List<Integer> scores = new LinkedList<>();
    private List<String> names = new LinkedList<>();

    public FileHandler(){
        readHighscores();
    }

    private void readHighscores() {

        String fileName = "./scores.txt";
        String line;

        BufferedReader bufferedReader = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            boolean checkifname = true;

            while ((line = bufferedReader.readLine()) != null) {
                if (checkifname) {
                    names.add(line);
                    checkifname = false;
                } else {
                    scores.add(Integer.parseInt(line));
                    checkifname = true;
                }
            }

            bufferedReader.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        finally {
            try {
                bufferedReader.close();
            } catch (Exception e){

            }
        }
    }

    public void writeHighscores(String name, int score) {
        File file = new File("./scores.txt");

        BufferedWriter bw = null;

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);

            if (names.size() == 0) {
                names.add(name);
                scores.add(score);
            } else {
                boolean added = false;

                for (int i = 0; i < names.size(); i++) {
                    if (!added && scores.get(i) < score) {
                        names.add(i, name);
                        scores.add(i, score);
                        added = true;
                    }
                }

                if (!added) {
                    names.add(name);
                    scores.add(score);
                }

                if (names.size()>5){
                    names.remove(names.size()-1);
                    scores.remove(scores.size()-1);
                }
            }

            for (int i = 0; i < scores.size(); i++) {
                bw.write(names.get(i));
                bw.newLine();
                bw.write("" + scores.get(i));
                bw.newLine();
            }

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            try {
                bw.close();
            } catch (Exception e){

            }
        }
    }

    public List<Integer> getScores() {
        return scores;
    }

    public List<String> getNames() {
        return names;
    }
}

