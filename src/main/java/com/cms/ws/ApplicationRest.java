package com.cms.ws;

import java.util.logging.Logger;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.service.RepositoryService;

@RestController
@RequestMapping("/api/application")
public class ApplicationRest {
	private final static Logger LOGGER = Logger.getLogger(ApplicationRest.class.getName());
	@Autowired
	private RepositoryService repositoryService;

//    @RequestMapping(value = "/get", method = RequestMethod.GET)
//    public List<DayOff> get(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date beginDate, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
//                            @RequestAttribute(Constants.USER_ID_ATTRIBUTE) Long userId) {
//        return dayOffService.findByDate(beginDate, endDate, userId);
//    }

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public void create(@RequestParam String value) throws LoginException, RepositoryException {
		repositoryService.addNode("wxcxqsdwcxw" + value);
	}

//    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
//    public void delete(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, @RequestAttribute(Constants.USER_ID_ATTRIBUTE) Long userId) {
//        dayOffService.delete(date, userId);
//    }
}