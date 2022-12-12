package edu.kmaooad;

import edu.kmaooad.commandDispatcher.CommandDispatcher;
import edu.kmaooad.commandHandlers.CommandHandler;
import edu.kmaooad.commandHandlers.CreateAccessRuleHandler;
import edu.kmaooad.commandHandlers.OtherCommandHandler;
import edu.kmaooad.processing.CommandParser;
import edu.kmaooad.repositories.AccessRuleRepository;
import edu.kmaooad.services.interfaces.AccessRuleService;
import edu.kmaooad.services.interfaces.BanDepartmentService;
import edu.kmaooad.services.interfaces.BanOrganizationService;
import edu.kmaooad.services.interfaces.BanUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Map;

@SpringBootTest
public class BaseTest {

    @Autowired
    protected AccessRuleService accessRuleService;
    @Autowired
    protected AccessRuleRepository accessRuleRepository;
    @Autowired
    protected BanUserService banUserService;
    @Autowired
    protected BanDepartmentService banDepartmentService;
    @Autowired
    protected BanOrganizationService banOrganizationService;
    @Autowired
    protected CommandDispatcher commandDispatcher;
    @Autowired
    protected Map<Long, CommandHandler> handlers;
    @Autowired
    protected CommandParser parser;
    @Autowired
    protected CreateAccessRuleHandler createAccessRuleHandler;
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected OtherCommandHandler otherCommandHandler;
}
