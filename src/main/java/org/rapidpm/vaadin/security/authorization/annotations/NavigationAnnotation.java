package org.rapidpm.vaadin.security.authorization.annotations;

import org.rapidpm.vaadin.security.authorization.api.AccessEvaluator;

import java.lang.annotation.*;

/**
 * This annotation is to be placed on other annotations, marking them as restriction-annotations and
 * assigning {@link AccessEvaluator}s to them. Take for example the case that a certain route-target
 * is only to be accessed by users that have the role 'administrator'. Now the first step would be
 * to create an annotation called VisibleTo and annotate it with NavigationAnnotation
 *
 * <pre>
 *     &#064;Retention(RUNTIME)
 *     &#064;NavigationAnnotation(RoleBasedAccessEvaluator.class)
 *     public &#064;interface VisibleTo {
 *         UserRoleDescription value();
 *     }
 * </pre>
 * <p>
 * The RoleBasedAccessEvaluator is an {@link AccessEvaluator} that could look something like the
 * following. Note that the generic type for this AccessEvaluator is the type of the annotation and
 * the annotation is the last parameter of 'evaluate'.
 *
 * <pre>
 * class RoleBasedAccessEvaluator implements AccessEvaluator&lt;VisibleTo&gt; {
 *
 *     Supplier&lt;UserRoleDescription&gt; userRoleProvider;
 *
 *     &#064;Override
 *     public Access evaluate(Location location, Class&lt;?&gt; navigationTarget, VisibleTo annotation) {
 *         final boolean hasRole = annotation.value().equals(userRoleProvider.get());
 *
 *         return hasRole ? Access.granted() : Access.restricted(UserNotInRoleException.class);
 *     }
 * }
 * </pre>
 * <p>
 * VisibleTo can then be used to prevent users that don't have the required role to enter the
 * route-target by just annotating the respective class
 *
 * <pre>
 *     &#064;Route("adminview")
 *     &#064;VisibleTo(UserRole.Admin)
 *     public class AdminView extends Div {
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface NavigationAnnotation {
  /**
   * The {@link AccessEvaluator} that is to be assigned to the annotation.
   *
   * @return the {@link AccessEvaluator}
   */
  Class<? extends AccessEvaluator<? extends Annotation>> value();
}
