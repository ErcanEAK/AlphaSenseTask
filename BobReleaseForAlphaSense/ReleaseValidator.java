import java.io.*;
import java.util.*;

public class ReleaseValidator {
    
    static class Release {
        int day;
        int duration;

        Release(int day, int duration) {
            this.day = day;
            this.duration = duration;
        }
    }

    public static void main(String[] args) {

        // Read releases from the file
        List<Release> releases = readFile("releases.txt");
           

        // Sort releases by end day (day + duration)
        releases.sort(Comparator.comparingInt(r -> r.day + r.duration));

        List<Release> selectedReleases = new ArrayList<>();
        int currentDay = 1;

        for (Release release : releases) {
            // Determine the end day
            int startDay = release.day;
            int endDay = startDay + release.duration - 1;

            // Check if the release can be validated within the sprint
            if (startDay <= 10 && endDay <= 10 && currentDay <= startDay) {
                selectedReleases.add(release);
                currentDay = endDay + 1; // Move the current day to the next available day
            }
        }

        // Write the output to solution.txt
        writeToFile(selectedReleases, "solution.txt");
       
    }
    //read the file base on the txt
public static List<Release> readFile (String path){
    List<Release> releases= new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            int day = Integer.parseInt(parts[0]);
            int duration = Integer.parseInt(parts[1]);
            releases.add(new Release(day, duration));
        }
    } catch (IOException e) {
        e.printStackTrace();
        return releases;
    }
    return releases;
}

    public static void writeToFile (List<Release> selectedReleases, String path){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write(String.valueOf(selectedReleases.size()));
            bw.newLine();
            for (Release release : selectedReleases) {
                int startDay = release.day;
                int endDay = startDay + release.duration - 1;
                bw.write(startDay + " " + endDay);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}