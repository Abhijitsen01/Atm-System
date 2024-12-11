import java.util.Scanner;

public class GradeCalculator {

    public static String calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return "A+";
        } else if (averagePercentage >= 80) {
            return "A";
        } else if (averagePercentage >= 70) {
            return "B";
        } else if (averagePercentage >= 60) {
            return "C";
        } else if (averagePercentage >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();

        
        double[] marks = new double[numSubjects];

        
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter marks obtained in subject " + (i + 1) + " (out of 100): ");
            marks[i] = scanner.nextDouble();
        }

       
        double totalMarks = 0;
        for (double mark : marks) {
            totalMarks += mark;
        }

        
        double averagePercentage = totalMarks / numSubjects;

        
        String grade = calculateGrade(averagePercentage);

        
        System.out.printf("\nTotal Marks: %.2f out of %.2f\n", totalMarks, (double)(numSubjects * 100));
        System.out.printf("Average Percentage: %.2f%%\n", averagePercentage);
        System.out.println("Grade: " + grade);

        
        scanner.close();
    }
}