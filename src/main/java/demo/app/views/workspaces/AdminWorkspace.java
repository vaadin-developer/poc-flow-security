package demo.app.views.workspaces;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

public class AdminWorkspace
    extends Composite<Div> {
  public AdminWorkspace() {
    getContent().add(new Span("AdminWorkspace"));
  }
}
