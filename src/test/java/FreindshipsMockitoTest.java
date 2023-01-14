import org.example.FriendsCollection;
import org.example.FriendshipsMongo;
import org.example.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

public class FreindshipsMockitoTest {

    @Mock
    private FriendsCollection friendsCollection;

    private FriendshipsMongo friendships;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        friendships = new FriendshipsMongo(friendsCollection);
    }

    @Test
    public void test_check_if_have_friends() {
        Person john = new Person("John");
        john.addFriend("Alice");
        john.addFriend("Peter");
        john.addFriend("Bob");

        when(friendsCollection.findByName("John")).thenReturn(john);

        List<String> friends = friendships.getFriendsList("John");

        //when(friendships.getFriendsList("John")).thenReturn(john);

        assertThat(friendships.getFriendsList("John"))
                .hasSize(3)
                .containsOnly("Alice", "Bob", "Peter");
    }

    @Test
    public void test_to_return_empty_list() {
        when(friendsCollection.findByName("John")).thenReturn(null);

        assertThat(friendships.getFriendsList("John")).isEmpty();
    }

    @Test
    public void test_if_make_friend() {
        String marekName = "Marek";
        String alaName = "Ala";
        Person john = new Person(marekName);
        Person alice = new Person(alaName);

        when(friendsCollection.findByName(marekName)).thenReturn(john);
        when(friendsCollection.findByName(alaName)).thenReturn(alice);

        friendships.makeFriends(marekName, alaName);

        assertThat(john.getFriends())
                .hasSize(1)
                .containsOnly(alaName);
        assertThat(alice.getFriends())
                .hasSize(1)
                .containsOnly(marekName);
    }

    @Test
    void test_if_are_friends() {
        Person john = new Person("John");
        john.addFriend("Alice");

        when(friendsCollection.findByName("John")).thenReturn(john);

        assertThat(friendships.areFriends("John", "Bob")).isFalse();
    }


    @Test
    public void test_check_if_friends() {
        Person john = new Person("Wanda");
        john.addFriend("Ola");

        when(friendsCollection.findByName("Wanda")).thenReturn(john);

        assertThat(friendships.areFriends("Wanda", "Ola")).isTrue();
    }

    @Test
    public void test_add_friend() {
        Person john = new Person("John");

        when(friendsCollection.findByName("John")).thenReturn(john);

        friendships.addFriend("John", "Alice");

        assertThat(john.getFriends())
                .hasSize(1)
                .containsOnly("Alice");
    }

    @Test
    public void shouldAddFriend() {
        Person john = new Person("John");

        when(friendsCollection.findByName("John")).thenReturn(john);

        friendships.addFriend("John", "Alice");

        assertThat(john.getFriends())
                .hasSize(1)
                .containsOnly("Alice");
    }
}

