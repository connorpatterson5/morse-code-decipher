import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class Node { // Used for tree onstruction
    char key;
    Node left, right;
 
    public Node(char item)
    {
        key = item;
        left = right = null;
    }
}

class BinaryTree { // Used for morse code deciphering
    Node root; 

    BinaryTree(char key){
        root = new Node(key);
    }

    BinaryTree(){
        root = null;
    }
}

public class MorseCode{
    
    private static void construct_tree(BinaryTree tree){
        // Construct tree to represent the logic to finding char representing morse code string
        // Messy but only way to make traversing/deciphering the easiest in my opinion
        // Note: http://www.learnmorsecode.com/ use this link for reference

        // A dash ("-") or "dah" means you go to the left of the tree and a dot (".") or "dit" means you
        // go to the right of the tree

        // Root
        tree.root = new Node('/');
    
        // Left side of tree
        tree.root.left = new Node('T');

        // Split into two children
        tree.root.left.left = new Node('M');
        tree.root.left.right = new Node('N');

        // M's children point to O and G

        // O's children points to filler spots, filler spots's children are 0, 8, and 9
        // Filler spots are required for this tree since this is what 0, 8, and 9 is in morse
        tree.root.left.left.left = new Node('O');
        tree.root.left.left.left.left = new Node('-');
        tree.root.left.left.left.right = new Node('-');
        tree.root.left.left.left.left.left = new Node('0');
        tree.root.left.left.left.left.right = new Node('9');
        tree.root.left.left.left.right.right = new Node('8');

        // G's children points to Q and Z, Z's only child is 7
        tree.root.left.left.right = new Node('G');
        tree.root.left.left.right.left = new Node('Q');
        tree.root.left.left.right.right = new Node('Z');
        tree.root.left.left.right.right.right = new Node('7');

        // N's children point to K and D
        
        // K's children points to Y and C
        tree.root.left.right.left = new Node('K');
        tree.root.left.right.left.left = new Node('Y');
        tree.root.left.right.left.right = new Node('C');

        // D's children points to X and B, B's only child is 6
        tree.root.left.right.right = new Node('D');
        tree.root.left.right.right.left = new Node('X');
        tree.root.left.right.right.right = new Node('B');
        tree.root.left.right.right.right.right = new Node('6');


        // Right side of tree
        tree.root.right = new Node('E');

        //Split into two children
        tree.root.right.left = new Node('A');
        tree.root.right.right = new Node('I');

        // A's children point to W and R
        
        // W's children point to J and P, J's only child is 1
        tree.root.right.left.left = new Node('W');
        tree.root.right.left.left.left = new Node('J');
        tree.root.right.left.left.right = new Node('P');
        tree.root.right.left.left.left.left = new Node('1');

        // R's only child is L
        tree.root.right.left.right = new Node('R');
        tree.root.right.left.right.right = new Node('L');

        // I's children point to U and S

        // U's children are a filler spot (mentioned on O's branch) and F, filler spots's only child is 2
        tree.root.right.right.left = new Node('U');
        tree.root.right.right.left.left = new Node('-');
        tree.root.right.right.left.right = new Node('F');
        tree.root.right.right.left.left.left = new Node('2');

        // S's children are V and H, V's only child is 3 and H's children are 4 and 5
        tree.root.right.right.right = new Node('S');
        tree.root.right.right.right.left = new Node('V');
        tree.root.right.right.right.right = new Node('H');
        tree.root.right.right.right.left.left = new Node('3');
        tree.root.right.right.right.right.left = new Node('4');
        tree.root.right.right.right.right.right = new Node('5');

    }

    public static char convert_to_char(String code, Node tree){
        int index = 0;
        while((tree.left != null || tree.right != null) && index < code.length()){
            // Go through the tree and check if left or right nodes are null and if we are within the length of code
            if(code.charAt(index) == '.'){ // Description of traversal is in construct_tree constructor
                tree = tree.right; 
            } else {
                tree = tree.left;
            }
            index++;
        }
        return tree.key; // Return corresponding character

    }

    public static String spaces(Scanner sc, BinaryTree tree){
        int index = 0; // Used to find index of string
        char char_to_add; // Character to add to final message
        String final_message = ""; // Message to return at en
        String line = ""; // Line variable that keeps track of the ONLY line in text file
        String temp_str = ""; // Temporary string that keeps track of current morse code sequence (Ex: ".-.")
        if(sc.hasNextLine()){
            line = sc.nextLine();
        }
        while(index < line.length()){ // Go through each char of the line
            if(line.charAt(index) == '/' || line.charAt(index) == ' '){ // If the char is a forward slash or space
                char_to_add = convert_to_char(temp_str, tree.root); // Convert our current temp_str to a character
                if(line.charAt(index) == '/'){ // Check to see if we need to add a space to final message
                    final_message += char_to_add + " ";
                } else {
                    final_message += char_to_add; // Otherwise just append only the character
                }
                temp_str = ""; // Reset temp string for next morse code sequence to convert
            } else {
                temp_str += line.charAt(index); // If it's just part of the sequence, append to current morse code sequence
            }

            // Need this in here, or else program will not account for last morse code sequence b/c temp_str would never
            // get used at the end
            if(index + 1 == line.length()){ 
                char_to_add = convert_to_char(temp_str, tree.root);
                final_message += char_to_add;
            }
            index++;
        }
        return final_message;
    }

    public static String new_lines(Scanner sc, BinaryTree tree){
        char char_to_add; // Character to add to final message
        String final_message = ""; // Message to return at end
        String line; // Line variable that keeps track of each line in text file

        while (sc.hasNextLine()){ // Go through each line
            line = sc.nextLine();
            if(line.trim().isEmpty()){ // If line is empty, final_message adds a space
                final_message += " ";
            } else {
                char_to_add = convert_to_char(line, tree.root); // Otherwise find conversion for char
                final_message += char_to_add; // Add it to final final_message
            }
        }
        return final_message;
    }

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in); // Input on how to parse code
        int user_ans; // User answer variable
        BinaryTree tree = new BinaryTree(); // Create and construct morse code tree
        construct_tree(tree);
        while(true){
            System.out.println("Will the code be separated by forward slash's or new lines?\n0: /'s are spaces\n1: Empty lines are spaces (Each character should be on its own line)");
            user_ans = input.nextInt();
            if(user_ans == 0 || user_ans == 1){
                break;
            } else {
                System.out.println("Wrong Input");
            }
        }

        // CAN EDIT TXT FILES, JUST FOLLOW SAME PATTERN OR ELSE IT WILL BREAK
        if(user_ans == 0){
            File file = new File("decipher_spaces.txt");
            Scanner sc = new Scanner(file);
            // Makes it look cooler when decoding/compiling B)
            System.out.print("Decoding message.");
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("\nMessage:\n" + spaces(sc, tree));
        } else {
            File file = new File("decipher_lines.txt");
            Scanner sc = new Scanner(file);
            // Makes it look cooler when decoding/compiling B)
            System.out.print("Decoding message.");
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("\nMessage:\n" + new_lines(sc, tree));
        }
        
    }
}