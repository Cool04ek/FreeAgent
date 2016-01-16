package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.model.Message;
import com.free.agent.model.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

/**
 * Created by antonPC on 29.07.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
@ActiveProfiles("test")
public class MessageDaoImplTest extends TestCase {
    private User u1, u2;
    private Message m1, m2, m3;

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;

    @Before
    public void init() {
        m1 = new Message(1l, "Learning", "Hello, I am learning");
        m1.setTimeOfCreate(new GregorianCalendar(2000, 1, 1).getTime());
        m1.setTimeOfRead(new Date());
        m2 = new Message(2l, "Play", "I like play");
        m2.setTimeOfCreate(new GregorianCalendar(2000, 1, 2).getTime());
        m3 = new Message(2l, "Play", "I like play again");
        m3.setTimeOfCreate(new GregorianCalendar(2000, 1, 3).getTime());

        u1 = new User("l1", "p1", "11-22-33");
        u1.setFirstName("Anton");
        u1.setLastName("Petrov");
        u1.setDateOfBirth(new GregorianCalendar(1991, 4, 3).getTime());
        u2 = new User("l2", "p2", "12-34-45");
        u2.setFirstName("Alenochka");
        u2.setLastName("Mosenko");
        u2.setDateOfBirth(new GregorianCalendar(1992, 4, 3).getTime());

        u1.getMessages().add(m1);
        u1.getMessages().add(m2);
        u1.getMessages().add(m3);
        m1.setUser(u1);
        m2.setUser(u1);
        m3.setUser(u1);

        userDao.create(u1);
        userDao.create(u2);
    }

    @Test
    public void createReadUpdateDeleteTest() {
        messageDao.deleteAll();
        assertEquals(0, messageDao.findAll().size());
        messageDao.create(new Message(5l, "Title", "Text"));
        assertEquals(1, messageDao.findAll().size());
        Message m1 = messageDao.findAll().get(0);
        m1.setText("Text2");
        messageDao.update(m1);
        assertEquals("Text2", messageDao.findAll().get(0).getText());
        messageDao.deleteAll();
        assertEquals(0, messageDao.findAll().size());
    }

    @Test
    public void findAllByReceiver() {
        assertEquals(3, messageDao.findAllByReceiver(u1.getEmail()).size());
        assertContainsMessage(messageDao.findAllByReceiver(u1.getEmail()), Lists.newArrayList(m1.getText(), m2.getText(), m3.getText()));
        assertEquals(0, messageDao.findAllByReceiver(u2.getEmail()).size());
    }

    @Test
    public void findAllByAuthor() {
        assertEquals(1, messageDao.findAllByAuthorEmailAndId(1l).size());
        assertEquals(2, messageDao.findAllByAuthorEmailAndId(2l).size());
        assertContainsMessage(messageDao.findAllByAuthorEmailAndId(2l), Lists.newArrayList(m2.getText(), m3.getText()));
    }

    @Test
    public void findAllByReceiverAndAuthor() {
        assertEquals(1, messageDao.findAllByReceiverAndAuthor(u1.getId(), 1l).size());
        assertEquals(2, messageDao.findAllByReceiverAndAuthor(u1.getId(), 2l).size());
        assertContainsMessage(messageDao.findAllByReceiverAndAuthor(u1.getId(), 2l), Lists.newArrayList(m2.getText(), m3.getText()));
        assertEquals(0, messageDao.findAllByReceiverAndAuthor(u2.getId(), 1l).size());
    }

    @Test
    public void findOlderThen() {
        assertEquals(3, messageDao.findOlderThen(new GregorianCalendar(2000, 1, 4).getTime()).size());
        assertEquals(3, messageDao.findOlderThen(new GregorianCalendar(2000, 1, 3).getTime()).size());
        assertEquals(2, messageDao.findOlderThen(new GregorianCalendar(2000, 1, 2).getTime()).size());
        assertEquals(1, messageDao.findOlderThen(new GregorianCalendar(2000, 1, 1).getTime()).size());
        assertEquals(0, messageDao.findOlderThen(new GregorianCalendar(1999, 12, 12).getTime()).size());
    }

    @Test
    public void getParticipants() {
        assertEquals(2, messageDao.getParticipants(u1.getId()).size());
        assertEquals(0, messageDao.getParticipants(u2.getId()).size());
        assertContainsMessage(Sets.newHashSet(m1.getAuthorId(), m2.getAuthorId(), m3.getAuthorId()), messageDao.getParticipants(u1.getId()));
    }

    @Test
    public void countUnreadMessages() {
        assertEquals(2, messageDao.countUnreadMessages(u1.getEmail()));
        assertEquals(0, messageDao.countUnreadMessages(u2.getEmail()));
    }

    private void assertContainsMessage(Set<Long> users, Set<Long> findParticipants) {
        for (Long participant : findParticipants) {
            assertTrue(users.contains(participant));
        }
    }

    private void assertContainsMessage(Set<Message> usersMessages, List<String> messages) {
        for (Message usersMessage : usersMessages) {
            assertTrue(messages.contains(usersMessage.getText()));
        }
    }

}
