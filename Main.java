import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
// try with resources?
public class Main {
    public static void main(String[] args) {
        // Initalize objects
        // Main part of program
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                // Select action
                printf("\033c");
                printf("""
                    To-Do List!
                    ___________


                    What would you like to do?
                    [1] Create a new list
                    [2] Read an existing list
                    [3] Update an existing list
                    [4] Delete an existing list
                    [5] Exit program
                    >""");
                int action = scanner.nextInt();
                scanner.nextLine();

                // File operations and stuff
                printf("\033c");
                switch (action) {
                    case 1 -> {
                        // Create a new list
                        printf("""
                            [1] Create a new list
                            _____________________
                            

                            What is the name of your list?\n>""");
                        String name = scanner.nextLine();
                        TodoOperations.writeList(name, "");
                        if (Files.exists(Path.of("lists/"+name))) {
                            printf("\nFile creation successful.");
                        } else {
                            printf("\nFile creation unsuccessful.");
                        }
                    }
                    case 2 -> {
                        // Read an existing list
                        printf("""
                            [2] Read an existing list
                            _________________________
                            
                        
                            """);
                        List<String> directory = TodoOperations.listDir();
                        if (directory.isEmpty()) {
                            printf("There are no todo lists available.");
                        } else {
                            printf("Which list do you wanna read? (0 to view all)\n");
                            for (int i = 0; i < directory.size(); i++) {
                                String k = directory.get(i);
                                printf("[%d] %s\n", i+1, k);
                            }
                            printf(">");
                            int listIndex = scanner.nextInt()-1;
                            scanner.nextLine();
                            if (listIndex == -1) {
                                printf("\033cHere are the contents of all your lists:\n\n");
                                for (int i = 0; i < directory.size(); i++) {
                                    String k = directory.get(i);
                                    List<String> contentList = TodoOperations.readList(directory.get(i));
                                    printf("%d. %s\n", i+1, k);
                                    for (int l = 0; l < contentList.size(); l++) {
                                        printf("\t%d. %s\n", l+1, contentList.get(l));
                                    }
                                }
                                printf("\nAnd that should be it");
                            } else {
                                printf("\033c");
                                List<String> contentList = TodoOperations.readList(directory.get(listIndex));
                                printf("Here are the contents of \"%s\":\n", directory.get(listIndex));
                                for (int i = 0; i < contentList.size(); i++) {
                                    printf("\n%d. %s", i+1, contentList.get(i));
                                }
                            }
                        }
                    }
                    case 3 -> {
                        // Update an existing list
                        printf("""
                            [3] Update an existing list
                            _________________________
                            
                        
                            """);
                        List<String> directory = TodoOperations.listDir();
                        if (directory.isEmpty()) {
                            printf("There are no todo lists available.");
                        } else {
                            printf("Select the list you want to configure\n");
                            for (int i = 0; i < directory.size(); i++) {
                                String k = directory.get(i);
                                printf("[%d] %s\n", i+1, k);
                            }
                            printf(">");
                            int listIndex = scanner.nextInt()-1;
                            scanner.nextLine();
                            String fileName = directory.get(listIndex);
                            printf("\033cSelected \"%s\"\n", fileName);
                            List<String> contentList = TodoOperations.readList(directory.get(listIndex));
                            printf("""
                                   
                                   Which operation would you like to do?
                                   [1] Set status of an item
                                   [2] Rewrite an item
                                   [3] Add a new item
                                   [4] Delete an item
                                   >""");
                            int operation = scanner.nextInt();
                            
                            printf("\n\nHere are the contents of \"%s\":\n", directory.get(listIndex));
                            for (int i = 0; i < contentList.size(); i++) {
                                printf("\n%d. %s", i+1, contentList.get(i));
                            }
                        
                            printf("\n\nWhich line do you want to act on (Enter 0 for last line or if empty)?\n>");
                            int line = scanner.nextInt();
                            scanner.nextLine();
                            if (line >= 0 && line <= contentList.size()) {
                                if (line == 0) {
                                    line = contentList.size();
                                }
                                int lineIndex = 0;
                                if (line != 0) {
                                    lineIndex = line - 1;
                                }
                                switch (operation) {
                                    case 1 -> {
                                        if (contentList.isEmpty()){
                                            printf("\033You cannot do this operation on an empty list.");
                                        } else {
                                            printf("\033cYou are setting the status of your item at line %d\n", line);
                                            for (int i = 0; i < contentList.size(); i++) {
                                                printf("\n%d. %s", i+1, contentList.get(i));
                                            }
                                            printf("""


                                                What would you like to do?
                                                [1] Mark it as complete (check)
                                                [2] Mark it as incomplete (uncheck)
                                                >""");
                                            int mark = scanner.nextInt();
                                            scanner.nextLine();
                                            if (mark != 1 && mark != 2) {
                                                printf("\nError. Please enter 1 or 2.");
                                            } else {
                                                String replacer = mark == 1 ? "X" : " ";
                                                String string = contentList.get(lineIndex);
                                                string = "["+replacer+string.substring(2);
                                                contentList.set(lineIndex, string);
                                                printf("\nSuccessfully changed status of item");
                                            }
                                        }
                                    }
                                    case 2 -> {
                                        if (contentList.isEmpty()){
                                            printf("\033You cannot do this operation on an empty list.");
                                        } else {
                                            printf("\033cYou are rewriting your item at line %d\n", line);
                                            for (int i = 0; i < contentList.size(); i++) {
                                                printf("\n%d. %s", i+1, contentList.get(i));
                                            }
                                            printf("\n\n\nBefore: %s\nAfter: ", contentList.get(lineIndex).substring(5));
                                            String words = scanner.nextLine();
                                            contentList.set(lineIndex, contentList.get(lineIndex).substring(0, 4)+words);
                                            printf("\nSuccessfully rewrote your item");
                                        }
                                    }
                                    case 3 -> {
                                        printf("\033cYou are adding new items to your list at line %d\n", line);
                                        for (int i = 0; i < contentList.size(); i++) {
                                            printf("\n%d. %s", i+1, contentList.get(i));
                                        }
                                        printf("\nHow many items would you like to add?\n>");
                                        int items = scanner.nextInt();
                                        scanner.nextLine();

                                        printf("\nEnter your %d item(s) here:\n", items);
                                        List<String> addItems = new ArrayList<>();
                                        for (int i = 0; i < items; i++) {
                                            printf(">");
                                            addItems.add("[ ] "+scanner.nextLine().trim());
                                        }
                                        for (int i = addItems.size()-1; i>=0; i--) {
                                            contentList.add(lineIndex, addItems.get(i));
                                        }
                                        printf("\nYour items are added.");
                                    }
                                    case 4 -> {
                                        if (contentList.isEmpty()){
                                            printf("\033You cannot do this operation on an empty list.");
                                        } else {
                                            printf("\033cYou are deleting your item at line %d\n", line);
                                            for (int i = 0; i < contentList.size(); i++) {
                                                printf("\n%d. %s", i+1, contentList.get(i));
                                            }
                                            printf("\n\nAre you sure you want to delete?\n[1] Yes\n[2] No\n>");
                                            int confirmation = scanner.nextInt();
                                            scanner.nextLine();
                                            if (confirmation != 1 && confirmation!= 2) {
                                                printf("\nPlease enter 1 or 2");
                                            } else {
                                                contentList.remove(lineIndex);
                                                printf("\nSuccessfully deleted your item");
                                            }
                                        }
                                    }
                                }

                                TodoOperations.writeList(fileName, contentList);
                            } else {
                                printf("\nError. The line you entered is out of range.");
                            }
                        }
                    }
                    case 4 -> {
                        // Delete an existing list
                        printf("""
                            [4] Delete an existing list
                            _________________________
                            
                        
                            """);
                        List<String> directory = TodoOperations.listDir();
                        if (directory.isEmpty()) {
                            printf("There are no todo lists available.");
                        } else {
                            printf("Which list do you wanna delete?\n");
                            for (int i = 0; i < directory.size(); i++) {
                                String k = directory.get(i);
                                printf("[%d] %s\n", i+1, k);
                            }
                            printf(">");
                            int listIndex = scanner.nextInt()-1;
                            scanner.nextLine();
                            String listName = directory.get(listIndex);
                            TodoOperations.delFile(listName);
                            if (!Files.exists(Path.of("lists/"+listName))) {
                                printf("\nFile deletion successful.");
                            } else {
                                printf("\nFile deletion unsuccessful.");
                            }
                        }
                    }
                    default -> {
                        // Exit program
                        printf("Exiting program.");
                        System.exit(0);
                    }
                }
                printf("\n\nPress enter to continue.\n");
                scanner.nextLine();
            }
        } catch (IOException e) {
            printf("\033cError, file doesnt exist, is in accessible, etc. Try again or exit the program.");
            System.exit(1);
        } catch (IndexOutOfBoundsException e) {
            printf("\033cError, you entered a number outside of the valid range.");
            System.exit(1);
        } catch (InputMismatchException e) {
            printf("\033cError, When asked for an integer, dont enter text.");
            System.exit(1);
        } catch (Exception e) {
            printf("\033cError, somethings wrong (prolly with the code).");
            System.exit(1);
        }
    }

    public static void printf(String string, Object... args) {
        System.out.printf(string, args);
    }
}