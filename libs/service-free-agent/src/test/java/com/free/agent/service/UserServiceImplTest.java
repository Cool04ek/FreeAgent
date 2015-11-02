package com.free.agent.service;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.SportDao;
import com.free.agent.dao.UserDao;
import com.free.agent.model.Sport;
import com.free.agent.model.User;
import com.free.agent.service.dto.UserRegistrationDto;
import com.free.agent.service.impl.UserServiceImpl;
import com.free.agent.service.util.EncryptionUtils;
import com.free.agent.service.util.ExtractFunction;
import com.google.common.collect.Sets;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by antonPC on 28.06.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml", "classpath*:free-agent-services-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER_TEST)
public class UserServiceImplTest extends TestCase {
    private static final String FOOTBALL = "FOOTBALL";

    @Mock
    private SportDao sportDao;

    @Mock
    private UserDao userDao;

    @Mock
    private MailService mailService;

    @InjectMocks
    private UserServiceImpl service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveUserTest() {
        UserRegistrationDto user = new UserRegistrationDto();
        Mockito.when(sportDao.findByNames(Sets.newHashSet(FOOTBALL))).thenReturn(sports());
        Mockito.when(userDao.create(ExtractFunction.getUser(user))).thenReturn(user());
        User savedUser = service.save(user);
        Assert.assertEquals(1, savedUser.getSports().size());
        Assert.assertEquals(FOOTBALL, savedUser.getSports().iterator().next().getName());
    }

    private Set<Sport> sports() {
        Sport sport = new Sport();
        sport.setName(FOOTBALL);
        return Sets.newHashSet(sport);
    }

    private User user() {
        User user = new User();
        user.setEmail("email@gmail.com");
        user.setPassword(EncryptionUtils.encrypt("12345"));
        user.setSports(Sets.newHashSet(new Sport(FOOTBALL)));
        return user;
    }
}
