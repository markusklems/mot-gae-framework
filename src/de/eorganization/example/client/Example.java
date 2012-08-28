package de.eorganization.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import de.eorganization.example.client.gui.MemberUpdatedHandler;
import de.eorganization.example.client.gui.TopLayout;
import de.eorganization.example.client.gui.canvas.LoginWindow;
import de.eorganization.example.client.gui.canvas.RegisterWindow;
import de.eorganization.example.client.gui.canvas.WelcomeInfoWindow;
import de.eorganization.example.client.model.LoginInfo;
import de.eorganization.example.client.model.Member;
import de.eorganization.example.client.services.LoginService;
import de.eorganization.example.client.services.LoginServiceAsync;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Example implements EntryPoint {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private LoginServiceAsync loginService = GWT.create(LoginService.class);

	/**
	 * Data
	 */

	private LoginInfo loginInfo;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					public void onFailure(Throwable error) {
					}

					public void onSuccess(LoginInfo result) {
						DOM.setStyleAttribute(RootPanel.get("loading")
								.getElement(), "display", "none");
						loginInfo = result;

						if (!loginInfo.isLoggedIn()) {
							createWelcomeLayout();
							if (getMember() != null) {
								new RegisterWindow(getMember()).show();
							}
						} else {
							createMasterLayout();
							if (getMember() != null
									&& getMember().isShowWelcomeInfo())
								new WelcomeInfoWindow(getMember(),
										new MemberUpdatedHandler() {

											@Override
											public void updated(Member member) {
												loginInfo.setMember(member);
											}
										}).show();
						}

					}
				});
	}

	private void createWelcomeLayout() {
		final Layout masterLayout = new VLayout();

		masterLayout.addMember(new TopLayout(loginInfo));

		VLayout welcomeInfo = new VLayout(10);
		welcomeInfo.setDefaultLayoutAlign(Alignment.CENTER);
		welcomeInfo.setAlign(VerticalAlignment.TOP);
		welcomeInfo.setHeight100();
		welcomeInfo.setWidth100();
		welcomeInfo.setBackgroundColor("#ffffff");

		Label welcomeLabel = new Label(
				"<h1 style=\"font-size: 40pt\">Welcome!</h1><p style=\"font-size: 20pt\"><span style=\"font-weight: bolder;\">The Example</span> <span style=\"font-style: italic; font-weight: bolder;\">n.</span> is an enhanced, continuously growing database of Amazon Machine Images (AMI). By permanently crawling public AMIs from the Amazon EC2 service, the Example collects information about the contents of AMIs, i.e., software libraries and versions. The large database supports DevOps, Cloud Developers and Cloud users with insights to compare and understand AMIs better. A search engine allows to find AMIs with a configuration that meets standards and requirements.</p><p style=\"font-size: 20pt\">Feel free to ride the Example and <a href=\"http://myownthemepark.com/prices-table/\">contact us for a premium account</a>. Simply sign in with a social user account (Facebook, Twitter, Google+).</p>");
		welcomeLabel.setWidth(600);
		welcomeLabel.setStyleName("welcome");

		Label loginAnchor = new Label(
				"<span style=\"font-size: 35pt; cursor: pointer; text-decoration: underline;\">Login</span>");
		loginAnchor.setAutoFit(true);
		loginAnchor.setWrap(false);
		loginAnchor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				new LoginWindow(loginInfo.getLoginUrl()).show();
			}
		});

		welcomeInfo.addMember(welcomeLabel);
		welcomeInfo.addMember(loginAnchor);

		masterLayout.addMember(welcomeInfo);

		masterLayout.setWidth100();
		masterLayout.setHeight100();
		masterLayout.setMaxHeight(700);
		masterLayout.draw();

	}

	private void createMasterLayout() {
		final Layout masterLayout = new VLayout();

		masterLayout.addMember(new TopLayout(loginInfo));
		// Your GWT GUI
		// masterLayout.addMember();

		masterLayout.setWidth100();
		masterLayout.setHeight100();
		masterLayout.setMaxHeight(700);
		masterLayout.draw();

		refresh();
	}

	private void refresh() {

	}

	private Member getMember() {
		return loginInfo != null ? loginInfo.getMember() : null;
	}

}
