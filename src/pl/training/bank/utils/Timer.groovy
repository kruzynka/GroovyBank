package pl.training.bank.utils


import org.codehaus.groovy.transform.GroovyASTTransformationClass


import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target


@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.METHOD])
@GroovyASTTransformationClass(["pl.training.bank.utils.WithTimer"])
@interface Timer {
}
