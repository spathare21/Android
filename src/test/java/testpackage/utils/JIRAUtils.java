package testpackage.utils;

/**
 * Created by dulari on 3/16/16.
 */

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.filter.*;



import java.io.IOException;
import java.net.URI;
import java.util.List;

import static com.google.common.collect.Iterables.transform;


public class JIRAUtils {


private static URI jiraServerUri = URI.create("http://jira.corp.ooyala.com");

        public static void createIssue(String issueSummary, String projectKey) throws IOException {
            final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, "playback-script", "Wf2rJvwx");

            try {
                final List<Promise<BasicIssue>> promises = Lists.newArrayList();
                final IssueRestClient issueClient = restClient.getIssueClient();

                System.out.println("Sending issue creation requests...");


                IssueInputBuilder issueBuilder = new IssueInputBuilder(projectKey, 1L);
                issueBuilder.setDescription("issue description");
                issueBuilder.setSummary("issue summary");

                for (int i = 0; i < 1; i++) {
                    final String summary = "Automation" + issueSummary;
                    //final IssueInput newIssue = new IssueInputBuilder(projectKey, 1L, summary).build();
                     final IssueInput newIssue = issueBuilder.build();

                    System.out.println("\tCreating: " + summary);
                    promises.add(issueClient.createIssue(newIssue));
                }

                System.out.println("Collecting responses...");
                final Iterable<BasicIssue> createdIssues = transform(promises, new Function<Promise<BasicIssue>, BasicIssue>() {

                    public BasicIssue apply(Promise<BasicIssue> promise) {
                        return promise.claim();
                    }
                });

                System.out.println("Created issues:\n" + Joiner.on("\n").join(createdIssues));

            } finally {
                restClient.close();
            }
        }

}
