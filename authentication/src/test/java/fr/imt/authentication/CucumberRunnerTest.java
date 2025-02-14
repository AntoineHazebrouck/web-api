package fr.imt.authentication;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = {
		"src/test/resources/features"
}, extraGlue = {
		"fr.imt.authentication.features"
})
public class CucumberRunnerTest {
}
