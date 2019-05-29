import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

public class PersonTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void person_instantiatesCorrectly_true()  {
        Person testPerson = new Person("Mike","[email protected]",0);
        assertEquals(true, testPerson instanceof Person);
    }

    @Test
    public void getName_personInstantiatesWithName_Mike() {
        Person testPerson = new Person("Mike", "[email protected]",0);
        assertEquals("Mike", testPerson.getName());
    }


    @Test
    public void getEmail_personInstantiateWithName_Email() {
        Person testPerson = new Person("Mike", "[email protected]",0);
        assertEquals("[email protected]", testPerson.getEmail());
    }
    @Test
    public void equals_returnsTrueIfNameAndEmailAreSame_true() {
        Person firstPerson = new Person("Mike", "[email protected]",0);
        Person anotherPerson = new Person("Mike", "[email protected]",0);
        assertTrue(firstPerson.equals(anotherPerson));
    }

    @Test
    public void save_insertsObjectIntoDatabase_Person() {
        Person testPerson = new Person("Henry", "[email protected]", 0);
        testPerson.save();
        assertTrue(Person.all().get(0).equals(testPerson));
    }

    @Test
    public void save_assignsIdToObject() {
        Person testPerson = new Person("Henry", "henry@henry.com", 0);
        testPerson.save();
        Person savedPerson = Person.all().get(0);
        assertEquals(testPerson.getId(), savedPerson.getId());
    }
}