/*
 * Copyright 1998-2012 Linux.org.ru
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ru.org.linux.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.org.linux.auth.AccessViolationException;
import ru.org.linux.site.Template;
import ru.org.linux.topic.Topic;
import ru.org.linux.topic.TopicDao;

import javax.servlet.ServletRequest;

@Controller
public class MemoriesController {
  @Autowired
  private TopicDao messageDao;

  @Autowired
  private MemoriesDao memoriesDao;

  @RequestMapping(value = "/memories.jsp", params = {"add"}, method = RequestMethod.POST)
  public @ResponseBody Integer add(
          ServletRequest request,
          @RequestParam("msgid") int msgid
  ) throws Exception {
    Template tmpl = Template.getTemplate(request);

    if (!tmpl.isSessionAuthorized()) {
      throw new AccessViolationException("Not authorized");
    }

    User user = tmpl.getCurrentUser();
    user.checkBlocked();
    user.checkAnonymous();

    Topic topic = messageDao.getById(msgid);
    if (topic.isDeleted()) {
      throw new UserErrorException("Тема удалена");
    }

    return memoriesDao.addToMemories(user.getId(), topic.getId());
  }

  @RequestMapping(value = "/memories.jsp", params = {"remove"}, method = RequestMethod.POST)
  public @ResponseBody void remove(
          ServletRequest request,
          @RequestParam("id") int id
  ) throws Exception {
    Template tmpl = Template.getTemplate(request);

    if (!tmpl.isSessionAuthorized()) {
      throw new AccessViolationException("Not authorized");
    }

    User user = tmpl.getCurrentUser();
    user.checkBlocked();
    user.checkAnonymous();

    MemoriesListItem m = memoriesDao.getMemoriesListItem(id);

    if (m != null) {
      if (m.getUserid() != user.getId()) {
        throw new AccessViolationException("Нельзя удалить чужую запись");
      }

      memoriesDao.delete(id);
    }
  }
}
