package com.company.input_output_stream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class Person implements Serializable {
    private String name = "Unknown";
    private String gender = "Unknown";
    private double height = Double.NaN;


    public Person(String name, String gender, double height) {
        this.name = name;
        this.gender = gender;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Person{"+
                "name='"+name+'\''+
                ", gender='"+gender+'\''+
                ", height="+height+
                '}';
    }

    private void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        // Throw an exception
        throw new IOException("Not meant for serialization!!!");
    }

    private void writeObject(ObjectOutputStream os) throws IOException {
        // Throw an exception
        throw new IOException("Not meant for serialization!!!");
    }

}

class LowerCaseReader extends FilterReader{

    /**
     * Creates a new filtered reader.
     *
     * @param in a Reader object providing the underlying stream.
     * @throws NullPointerException if {@code in} is {@code null}
     */
    protected LowerCaseReader(Reader in) {
        super(in);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int count  = super.read(cbuf,off, len);
        if (count != -1)
        {
            // Convert all to lower case
            int limit = off + count;
            {
                for (int i = off; i < limit; i++) {
                    cbuf[i] = Character.toLowerCase(cbuf[i]);
                }
            }
        }
        return count;
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
//        writeData("nguyen van vi", "data.txt");
//        readDataWithBuffered("data.txt");
//        exampleOfPiped();
//        exampleOfObjectSerialization("data.txt");
//        exampleOfAdvancedObjectSerialization("data.txt");
//        exampleOfWriterAndReader("data.txt");
        exampleOfRandomAccessFile("data.txt");
    }

    // Example of Random Access File
    public static void exampleOfRandomAccessFile(String fileName) throws IOException {
       File file = new File(fileName);
       if (!file.exists())
           initialWrite(fileName);
       readFile(fileName);
       readFile(fileName);
    }

    public static void readFile(String fileName) throws IOException {
        // open the file in the read-write mode
        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        // How it save ?
        int counter = raf.readInt();
        String msg = raf.readUTF();

        System.out.println("File Read Counter: " + counter);
        System.out.println("File Text: " + msg);
        System.out.println("----------------------------");

        // Increment the file read counter by 1
        incrementReadCounter(raf);

    }
    public static void incrementReadCounter(RandomAccessFile raf) throws IOException {
        // Read the current file pointer position so that we can restore it at the end
        long currentPosition = raf.getFilePointer();

        // Set the file pointer in the beginning
        raf.seek(0);

        // Read the counter and increment by 1
        int counter = raf.readInt();
        counter++;

        // Set the file pointer the zero again to overwrite the value of the counter
        raf.seek(0);
        raf.writeInt(counter);

        // Restore the file pointer
        raf.seek(currentPosition);
    }

    public static void initialWrite(String fileName)
    {
        // Open the file in the read mode
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw")) {
            // Write the file read counter as 0
            raf.writeInt(0);

            // Write the message
            raf.writeUTF("Hello World !");
        }catch (IOException e)
        {
            e.printStackTrace();
        }


    }


    // Example of Writer and Reader
    public static void exampleOfWriterAndReader(String fileName) {
        // Writer
        System.out.println("Start Writing: ");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.append("And now we reach'd the orchard-plot;");
            bw.newLine();
            bw.append("And, as we climb'd the hill,");
            bw.newLine();
            bw.append("The sinking moon to Lucy's cot");
            bw.newLine();
            bw.append("Came near and nearer still.");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Start Reading: ");
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String text = null;
            while ((text = br.readLine()) != null) {
                System.out.println(text);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // Example of Advanced Object Serialization
    public static void exampleOfAdvancedObjectSerialization(String fileName) {
        // Serialization
        /*Person person = new Person("Nguyen Vi", "Male", 70);
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {
            // write the first
            oos.writeObject(person);
            // Change name
            person.setName("SomeOne");
            person.setHeight(175);
            // write the second object
            oos.writeObject(person);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // Deserialization
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(fileName))) {
            // Read the two objects that were written in the serialize() method
            Person person1 = (Person) ois.readObject();
            Person person2 = (Person) ois.readObject();
            System.out.println("Reading person 1 info: ");
            System.out.println(person1);
            System.out.println("Reading person 2 info: ");
            System.out.println(person2);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    // Example of Object Serialization
    public static void exampleOfObjectSerialization(String fileName) {
        // Write a single person
        // Example List of Person

        // Data
        Person per = new Person("Nguyen Vi", "Male", 70);
        Person per1 = new Person("Nguyen Van", "Female", 65);
        List<Person> personList = new ArrayList<>();
        personList.add(per);
        personList.add(per1);


        System.out.println("Start Writing Object");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(personList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Start Reading Object");
        // Read a single object
        // Write a single person
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            List<Person> personList1 = (List<Person>) ois.readObject();
            System.out.println(personList1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Example of Piped
    public static void exampleOfPiped() {
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream();
        try {
            pos.connect(pis);
            Runnable producer = () -> produceData(pos, "data.txt");
            Runnable consumer = () -> consumerData(pis);

            new Thread(producer).start();
            new Thread(consumer).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void consumerData(PipedInputStream pis) {
        try {
            int num = -1;
            while ((num = pis.read()) != -1) {
                System.out.println("Reading : "+num+" char : "+(char) num);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO Fix pipe broke because of not send -1
    public static void produceData(PipedOutputStream pos, String fileName) {

        // Produce from an array
        /*try {
            for (int i = 0; i <= 50; i++) {
                // write() take in
                // pos.write(i);
                // write() take byte
                pos.write((byte) i);
                pos.flush();
                System.out.println("Writing: "+i);
                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*/
        // Read from a file
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            int data;
            while ((data = fileInputStream.read()) != -1) {
                pos.write((byte) data); // the same as pos.write(int)
                pos.flush();
                System.out.println("Writing : "+data);
                Thread.sleep(500);
            }

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static void readDataWithBuffered(String fileName) {

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileName))) {
            int data;
            while ((data = bufferedInputStream.read()) != -1) {
                System.out.print((char) data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readData(String fileName) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            int data;
            byte byteData;
            while ((data = fileInputStream.read()) != -1) {
                // read() return int
                // int may be casted to byte
                // int may be casted to char
                byteData = (byte) data;
                System.out.println("Int Data "+data+" Char Data "+(char) data+" Byte Data "+byteData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeData(String data, String fileName) {

        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            fileOutputStream.write(bytes);
            fileOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
