import java.io.*;
import java.util.*;

class Student {
    private String name;
    private int rollNumber;
    private String grade;
    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    public void display() {
        System.out.println("Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade);
    }

    @Override
    public String toString() {
        return name + "," + rollNumber + "," + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students;
    private final String fileName = "students.txt";
    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadData();
    }
    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                int rollNumber = Integer.parseInt(data[1]);
                String grade = data[2];
                students.add(new Student(name, rollNumber, grade));
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
    private void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Student student : students) {
                bw.write(student.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    public void addStudent(String name, int rollNumber, String grade) {
        students.add(new Student(name, rollNumber, grade));
        saveData();
        System.out.println("Student added successfully!");
    }
    public void removeStudent(int rollNumber) {
        students.removeIf(student -> student.getRollNumber() == rollNumber);
        saveData();
        System.out.println("Student removed successfully!");
    }
    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            for (Student student : students) {
                student.display();
            }
        }
    }
    public void editStudent(int rollNumber, String name, String grade) {
        Student student = searchStudent(rollNumber);
        if (student != null) {
            student.setName(name);
            student.setGrade(grade);
            saveData();
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Student not found.");
        }
    }
    public boolean isValidInput(String input, String type) {
        if (type.equals("name") && input != null && !input.trim().isEmpty()) {
            return true;
        } else if (type.equals("rollNumber")) {
            try {
                Integer.parseInt(input);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (type.equals("grade") && input != null && !input.trim().isEmpty()) {
            return true;
        }
        return false;
    }
    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add a new student");
            System.out.println("2. Edit student information");
            System.out.println("3. Remove a student");
            System.out.println("4. Search for a student");
            System.out.println("5. Display all students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter student's name: ");
                    String name = sc.nextLine();
                    while (!isValidInput(name, "name")) {
                        System.out.print("Invalid name. Please enter again: ");
                        name = sc.nextLine();
                    }
                    System.out.print("Enter student's roll number: ");
                    String rollNum = sc.nextLine();
                    while (!isValidInput(rollNum, "rollNumber")) {
                        System.out.print("Invalid roll number. Please enter again: ");
                        rollNum = sc.nextLine();
                    }
                    int rollNumber = Integer.parseInt(rollNum);
                    System.out.print("Enter student's grade: ");
                    String grade = sc.nextLine();
                    while (!isValidInput(grade, "grade")) {
                        System.out.print("Invalid grade. Please enter again: ");
                        grade = sc.nextLine();
                    }
                    addStudent(name, rollNumber, grade);
                    break;

                case 2:
                    System.out.print("Enter student's roll number to edit: ");
                    int editRollNo = sc.nextInt();
                    sc.nextLine(); 
                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();
                    while (!isValidInput(newName, "name")) {
                        System.out.print("Invalid name. Please enter again: ");
                        newName = sc.nextLine();
                    }
                    System.out.print("Enter new grade: ");
                    String newGrade = sc.nextLine();
                    while (!isValidInput(newGrade, "grade")) {
                        System.out.print("Invalid grade. Please enter again: ");
                        newGrade = sc.nextLine();
                    }
                    editStudent(editRollNo, newName, newGrade);
                    break;

                case 3:
                    System.out.print("Enter student's roll number to remove: ");
                    int removeRollNo = sc.nextInt();
                    removeStudent(removeRollNo);
                    break;

                case 4:
                    System.out.print("Enter student's roll number to search: ");
                    int searchRollNo = sc.nextInt();
                    Student student = searchStudent(searchRollNo);
                    if (student != null) {
                        student.display();
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 5:
                    displayAllStudents();
                    break;

                case 6:
                    System.out.println("Exiting the application...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}

public class StudentManagementSystemApp {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.showMenu();
    }
}
