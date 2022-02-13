<%@ include file="/init.jsp" %>

<portlet:actionURL var="sendFormActionURL" name="<%= MVCCommandNames.ACTION_FORM %>"/>
<portlet:resourceURL id="<%=MVCCommandNames.RESOURCE_CAPTCHA%>" var="captchaResourceURL"/>


<aui:form action="${sendFormActionURL}" name="fm" method="post" >
	<div class="container">
		<div class="content-wrapper">
			<div class="row justify-content-center">
				<div class="col-12 col-lg-12 bg-white align-self-center d-flex flex-column text-center">
					<article class="mt-60 mb-60 pl-30 pr-30">
						<h2 class="font-bold font-30 mb-30">
							<liferay-ui:message key="formembeddedweb.title.h2"></liferay-ui:message>
						</h2>
						<p class="font-medium font-20 mb-15">
							<liferay-ui:message key="formembeddedweb.subtext"></liferay-ui:message>
						</p>
						<c:choose>
							<c:when test="${not empty formInstanceId}">
								<liferay-form:ddm-form-renderer ddmFormInstanceId="${formInstanceId}" ddmFormInstanceRecordId="${recordId}" showFormBasicInfo="${false}" showSubmitButton="${false}"/>
								<liferay-captcha:captcha url="${captchaResourceURL}"/>
								<button id="fmButton" class="font-20 font-bold button-link-pink mb-30 mt-30" type="submit" >
									<liferay-ui:message key="formembeddedweb.submit.button"/>
								</button>
							</c:when>
							<c:otherwise>
								<p class="font-bold bg-custom-gray font-20 pb-60 pt-60 text-center m-0">
									<liferay-ui:message key="formembeddedweb.form.not.selected"></liferay-ui:message>
								</p>
							</c:otherwise>
						</c:choose>
					</article>
				</div>
			</div>
		</div>
	</div>
</aui:form>



