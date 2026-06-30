public class HashMap {
    // Person class to store the name and age of a person
    private static class Person {
        public String name;
        public int age;
        public Person next;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    private static int hash(String key, int tableSize) {
        int h = 0;
        for (int i = 0; i < key.length(); i++) {
            h = 31 * h + key.charAt(i);
        }
        return (h & 0x7fffffff) % tableSize;
    }

    // Table to store the people, along with the size of the table
    private Person[] table;
    private int size;

    public HashMap() {
        table = new Person[100];
        size = 0;
    }

    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.insert("John", 25);
        System.out.println(map.get("Jhn"));
    }

    // Insert a new person into the map
    public void insert(String name, int age) {
        Person currentNode = table[hash(name, table.length)];
        if (currentNode == null) {
            table[hash(name, table.length)] = new Person(name, age);
            size++;
            return;
        }
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }
        currentNode.next = new Person(name, age);
        size++;
    }

    // Get the age of a person by name
    public int get(String name) {
        if (table[hash(name, table.length)] == null) {
            return -1;
        }
        Person person = table[hash(name, table.length)];
        while (!table[hash(name, table.length)].name.equals(name)) {
            if (person.name != null || !person.name.equals(name)) {
                person = person.next;
            }

            if (person == null) {   
                return -1;
            } else {
                return person.age;
            }
        }
        return 0;
    }

    public int remove(String name) {
        if (table[hash(name, table.length)] == null) {
            return -1;
        }
        Person person = table[hash(name, table.length)];
        if (person.name.equals(name)) {
            table[hash(name, table.length)] = person.next;
            size--;
            return person.age;
        }
        while (person.next != null) {
            if (person.next.name.equals(name)) {
                Person temp = person.next;
                person.next = person.next.next;
                size--;
                return temp.age;
            }
            person = person.next;
        }
        return -1;

    }

    // Get the size of the map
    public int size() {
        return this.size;
    }
}