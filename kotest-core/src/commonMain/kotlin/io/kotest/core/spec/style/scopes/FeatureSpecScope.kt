package io.kotest.core.spec.style.scopes

import io.kotest.core.test.createTestName

/**
 * A scope that allows tests to be registered using the syntax:
 *
 * feature("some context")
 * xfeature("some test")
 *
 */
interface FeatureSpecScope : RootScope {

   fun feature(name: String, test: suspend FeatureScope.() -> Unit) = addFeature(name, false, test)
   fun xfeature(name: String, test: suspend FeatureScope.() -> Unit) = addFeature(name, true, test)

   fun addFeature(name: String, xdisabled: Boolean, test: suspend FeatureScope.() -> Unit) {
      val testName = createTestName("Feature: ", name)
      registration().addContainerTest(testName, xdisabled = xdisabled) {
         FeatureScope(description().append(testName), lifecycle(), this, defaultConfig()).test()
      }
   }
}
