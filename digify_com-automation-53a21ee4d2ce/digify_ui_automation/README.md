# Digify UI automation

UI automation end to end testing framework built with Playwright Java using Cucumber for BDD.
Internally the UI page clases are following the Page Object design pattern i.e. all locators of a UI page and its
respective actions & methods should be in a single class file (easy to maintain & extend).

The primary purpose of this framework is to automate the end to end(E2E) testing of Digify web application, ensuring its
functionality, reliability, and adherence to specifications.
Automated testing allows us to run huge test sets quickly which inturn helps in test coverage during release/regression
testing.

## Table of Contents

- [Installation](#installation-and-configuration)
- [Execution](#execution)
- [Reporting](#reporting)
- [Contribution](#contribution)
- [Push your code in Bitbuket](#push-your-code-in-bitbuket)
- [Naming convention for web element locators](#naming-convention-for-web-element-locators)

## Installation and Configuration

Prerequisite before setting up the project.
<li>Java version 20</li>
<li>Apache-maven-3.9.5</li>
<li>Git Bash on local</li>
<li>Clone the project in your local using git clone 'gitrepo ssh' </li>
<ul>
    <li>Install Intellij (community[free] version) </li>
    <li>Open Intellij</li>
        <ul>
            <li> Click on "main menu"</li>
            <li> Click on "open"</li>
            <li> Navigate to "digify_ui_automation" folder</li>
            <li> Click on pom.xml and click on "Ok" button</li>
            <li> Click on "Open as project"</li>
        </ul>
</ul>

## Execution

To execute the project, open cmd in automation folder and execute below command(open cmd in different desktop to not
impact manual work):
<ul>
<li> mvn test -DBROWSER_NAME=chrome -DHEADLESS=true -DTEST_ENV=staging</li>
<li>or open the CucumberTestRunner in Intellij, right-click on the page and then select Run 'CucumberTestRunner'</li>
</ul>

## Reporting

After execution of all the feature files, cucumber HTML report will get generated under target > cucumber_reports
folder.
<ul> 
<li>Navigate to <b>target/cucumber_reports/cucumberHTMLReport.html</b> in the project folder.</li>
<li>Right click on <b>cucumberHTMLReport.html</b> and select <b>open in browser</b> option.</li>
</ul>

## Contribution

<ul>
<li>Feature files
    <ul> 
    <li>Add new Features to <b>digifyE2E/features</b> folder</li>
    <li>All features should be tagged properly:
        <ul> 
        <li>Tags are a grouping mechanism in cucumber & should be added in a way which enables subset execution of tests</li>
        <li>Dont add any tags which are not agreed upon with the team</li>
        <li>Feature level tags: @exactNameOfFeatureFile @directoryNameTag @regression</li>
        <li>Scenario level tage: @smoke @prod</li>
        </ul>
    </li>
    </ul>
</li>
<li>Add respective step definition files in <b>digifyE2E/cucumber/stepDefinitions</b> folder</li>
<li>Add web locators and methods in the respective <b>page</b> folder</li>
<li>Add all the common, reusable methods in the <b>CommonUIActions.java</b> class</li>
<li>Add all the wrapper methods in the <b>Base.java</b> class</li>
<li>Dont use Hard waits for seconds anywhere in the stepdefinitions, instead wait for conditions like element to appear, to be clickable, etc.</li>
</ul>

## Push your code in Bitbuket

<ul>
<li>Take latest pull of <b>develop branch</b> from origin
<li>Create/checkout a new branch from <b>develop branch only.</b></li>
<li>Work on the assigned tasks within the feature branch. Regularly commit changes with meaningful messages to track progress.</li>
<li>Push your branch to remote</li>
<li>Create a pull request from your branch to develop branch, and get it review from team members </li>
<li>Once the feature branch is reviewed and passes all the feature files, merge it into the <b>develop</b> branch.</li>
<li>Periodically develop branch will be merged to master (frequency TBD) </li>
</ul>

## Naming convention for web element locators

<h4>Prefix the 'getter methods' for locators with below codes to make locators more readable:<h4>
<ul>
    <li>button: btn 
        <ul>
            <li>ex: public static final String 
btnCreateDataRoom = "//*[@id='cre_btn']";
        </ul>
    </li>
    <li>checkbox: chk</li>
    <li>label: lbl</li>
    <li>drop down: drd</li>
    <li>select drop down/dropdown value: sdd</li>
    <li>text box/input field: txt</li>
    <li>radio: rdo</li>
    </ul>


    
