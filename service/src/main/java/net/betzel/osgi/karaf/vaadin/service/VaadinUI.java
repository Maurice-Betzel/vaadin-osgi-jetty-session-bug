package net.betzel.osgi.karaf.vaadin.service;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.*;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.*;
import org.osgi.service.component.annotations.Component;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@Theme("valo")
@Push(PushMode.MANUAL)
public class VaadinUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        //new Navigator(this, this);
        final VerticalLayout layout = new VerticalLayout();

        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thank you " + name.getValue()
                    + ", Vaadin on OSGi works!"));
        });

        layout.addComponents(name, button);

        setContent(layout);
    }

    @Component(service = VaadinServlet.class)
    @WebServlet(urlPatterns = "/service/*", name = "VaadinUIServlet", asyncSupported = true,
            initParams = {
                    @WebInitParam(name = "org.atmosphere.websocket.suppressJSR356", value = "true"),
                    @WebInitParam(name = "org.atmosphere.websocket.maxIdleTime", value = "600000")
            }
    )
    @VaadinServletConfiguration(ui = VaadinUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet implements SessionInitListener {

        @Override
        public void sessionInit(SessionInitEvent sessionInitEvent) throws ServiceException {
            System.out.println("Session init event!");
        }
    }

}