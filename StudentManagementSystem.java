import java.util.*;

class Learner {
    private String fullName;
    private int id;
    private String birthDate;
    private String contactEmail;
    private String uniqueId;
    private String enrolledCourse;

    public Learner(String fullName, int id, String birthDate, String contactEmail, String uniqueId, String enrolledCourse) {
        this.fullName = fullName;
        this.id = id;
        this.birthDate = birthDate;
        this.contactEmail = contactEmail;
        this.uniqueId = uniqueId;
        this.enrolledCourse = enrolledCourse;
    }

    public String getFullName() { return fullName; }
    public int getId() { return id; }
    public String getBirthDate() { return birthDate; }
    public String getContactEmail() { return contactEmail; }
    public String getUniqueId() { return uniqueId; }
    public String getEnrolledCourse() { return enrolledCourse; }

    public void printProfile() {
        System.out.println("\n--- Learner Profile ---");
        System.out.println("Name         : " + fullName);
        System.out.println("ID           : " + id);
        System.out.println("Date of Birth: " + birthDate);
        System.out.println("Email        : " + contactEmail);
        System.out.println("Student ID   : " + uniqueId);
        System.out.println("Course       : " + enrolledCourse);
    }
}

class Module {
    private String moduleName;
    private int score;
    private String rating;
    private double percent;

    public Module(String moduleName, int score) {
        this.moduleName = moduleName;
        this.score = score;
        computeGrade();
    }

    private void computeGrade() {
        percent = score;
        if (score >= 90) rating = "A+";
        else if (score >= 80) rating = "A";
        else if (score >= 70) rating = "B+";
        else if (score >= 60) rating = "B";
        else if (score >= 50) rating = "C";
        else if (score >= 40) rating = "D";
        else rating = "F";
    }

    public void showModuleReport() {
        System.out.println("Module     : " + moduleName);
        System.out.println("Score      : " + score);
        System.out.println("Grade      : " + rating);
        System.out.println("Percentage : " + percent + "%\n");
    }

    public int getScore() { return score; }
}

class ResultCard {
    private Module[] modules = new Module[5];
    private int count = 0;
    private double overallPercent;
    private String finalGrade;

    public void insertModule(Module module) {
        if (count < 5) {
            modules[count++] = module;
            computeFinalResult();
        }
    }

    private void computeFinalResult() {
        int total = 0;
        for (int i = 0; i < count; i++) {
            total += modules[i].getScore();
        }
        overallPercent = (total / (double)(count * 100)) * 100;

        if (overallPercent >= 90) finalGrade = "A+";
        else if (overallPercent >= 80) finalGrade = "A";
        else if (overallPercent >= 70) finalGrade = "B+";
        else if (overallPercent >= 60) finalGrade = "B";
        else if (overallPercent >= 50) finalGrade = "C";
        else if (overallPercent >= 40) finalGrade = "D";
        else finalGrade = "F";
    }

    public void printReportCard() {
        System.out.println("\n--- Report Card ---");
        for (int i = 0; i < count; i++) {
            modules[i].showModuleReport();
        }
        System.out.printf("Final Percentage: %.2f%%\n", overallPercent);
        System.out.println("Overall Grade   : " + finalGrade);
    }
}

public class EduTrackSystem {
    private static Learner[] learners = new Learner[100];
    private static ResultCard[] reports = new ResultCard[100];
    private static int totalLearners = 0;
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- EduTrack Menu ---");
            System.out.println("1. Register Learner");
            System.out.println("2. Record Exam Results");
            System.out.println("3. View Learner Info");
            System.out.println("4. View Result Card");
            System.out.println("5. Exit");
            System.out.print("Choice: ");

            int option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1 -> registerLearner();
                case 2 -> recordResults();
                case 3 -> showLearnerInfo();
                case 4 -> showResultCard();
                case 5 -> {
                    System.out.println("Thank you! System Closed.");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void registerLearner() {
        if (totalLearners >= learners.length) {
            System.out.println("Capacity Full. Cannot register more.");
            return;
        }

        System.out.println("\nEnter Learner Info:");
        System.out.print("Name       : ");
        String name = input.nextLine();
        System.out.print("ID         : ");
        int id = input.nextInt(); input.nextLine();
        System.out.print("Birth Date : ");
        String dob = input.nextLine();
        System.out.print("Email      : ");
        String email = input.nextLine();
        System.out.print("Student ID : ");
        String sid = input.nextLine();
        System.out.print("Course     : ");
        String course = input.nextLine();

        learners[totalLearners] = new Learner(name, id, dob, email, sid, course);
        totalLearners++;
        System.out.println("Registration Successful!");
    }

    private static void recordResults() {
        System.out.print("Enter Learner ID: ");
        int id = input.nextInt(); input.nextLine();

        int index = searchLearner(id);
        if (index == -1) {
            System.out.println("Learner not found.");
            return;
        }

        ResultCard card = new ResultCard();
        for (int i = 1; i <= 5; i++) {
            System.out.print("Module " + i + " Name: ");
            String mod = input.nextLine();
            System.out.print("Score (0-100): ");
            int score = input.nextInt(); input.nextLine();

            if (score < 0 || score > 100) {
                System.out.println("Invalid input. Try again.");
                i--;
                continue;
            }
            card.insertModule(new Module(mod, score));
        }

        reports[index] = card;
        System.out.println("Results Recorded for " + learners[index].getFullName());
    }

    private static void showLearnerInfo() {
        System.out.print("Enter Learner ID: ");
        int id = input.nextInt(); input.nextLine();

        int index = searchLearner(id);
        if (index == -1) {
            System.out.println("Learner not found.");
            return;
        }

        learners[index].printProfile();
    }

    private static void showResultCard() {
        System.out.print("Enter Learner ID: ");
        int id = input.nextInt(); input.nextLine();

        int index = searchLearner(id);
        if (index == -1) {
            System.out.println("Learner not found.");
            return;
        }

        if (reports[index] == null) {
            System.out.println("No results available.");
            return;
        }

        reports[index].printReportCard();
    }

    private static int searchLearner(int id) {
        for (int i = 0; i < totalLearners; i++) {
            if (learners[i].getId() == id) {
                return i;
            }
        }
        return -1;
    }
}
