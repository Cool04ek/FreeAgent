package com.free.agent.service;

import com.free.agent.Filter;
import com.free.agent.model.User;
import org.apache.commons.fileupload.FileItem;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by antonPC on 21.06.15.
 */
public interface UserService {
    User save(User user, Set<String> names);

    void addImage(String login, List<FileItem> image);

    Collection<User> findAll();

    User findByLogin(String login);

    User findById(long id);

    Collection<User> findByFilter(Filter filter);
}
