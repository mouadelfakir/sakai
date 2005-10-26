/**********************************************************************************
*
* $Id$
*
***********************************************************************************
*
* Copyright (c) 2005 The Regents of the University of California, The Regents of the University of Michigan,
*                  Board of Trustees of the Leland Stanford, Jr., University, and The MIT Corporation
* 
* Licensed under the Educational Community License Version 1.0 (the "License");
* By obtaining, using and/or copying this Original Work, you agree that you have read,
* understand, and will comply with the terms and conditions of the Educational Community License.
* You may obtain a copy of the License at:
* 
*      http://cvs.sakaiproject.org/licenses/license_1_0.html
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
* INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
* AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
* DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*
**********************************************************************************/

package org.sakaiproject.tool.section.jsf.backingbean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.api.section.SectionManager;
import org.sakaiproject.api.section.coursemanagement.Course;
import org.sakaiproject.api.section.coursemanagement.CourseSection;
import org.sakaiproject.api.section.facade.Role;
import org.sakaiproject.tool.section.jsf.JsfUtil;

/**
 * Base class for all JSF backing beans relying on knowledge of the current
 * course context.
 * 
 * @author <a href="mailto:jholtzman@berkeley.edu">Josh Holtzman</a>
 *
 */
public class CourseDependentBean extends InitializableBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(CourseDependentBean.class);

	private transient CourseBean courseBean;

	private CourseBean getCourseBean() {
		if(courseBean == null) {
			courseBean = (CourseBean)JsfUtil.resolveVariable("courseBean");
		}
		return courseBean;
	}
	
	/**
	 * Gets the categories that are currently being used in this site context.
	 * 
	 * @param categories
	 * @param sections
	 * @return
	 */
	protected Set getUsedCategories() {
		Set used = new HashSet();
		List sections = getAllSiteSections();
		List categories = getSectionManager().getSectionCategories(getSiteContext());
		for(Iterator iter = sections.iterator(); iter.hasNext();) {
			CourseSection section = (CourseSection)iter.next();
			String cat = section.getCategory();
			if(categories.contains(cat)) {
				used.add(cat);
			}
		}
		return used;
	}

	protected SectionManager getSectionManager() {
		return getCourseBean().getSectionManager();
	}
	
	protected String getUserUid() {
		return getCourseBean().authn.getUserUid(FacesContext.getCurrentInstance().getExternalContext().getRequest());
	}
	
	protected Role getSiteRole() {
		return getCourseBean().authz.getSiteRole(getUserUid(), getSiteContext());
	}
	protected String getSiteContext() {
		return getCourseBean().context.getContext(null);
	}
	
	protected Course getCourse() {
		return getCourseBean().sectionManager.getCourse(getSiteContext());
	}
	
	protected List getAllSiteSections() {
		return getCourseBean().sectionManager.getSections(getSiteContext());
	}

	protected Set getMyEnrolledSections() {
		String userUid = getUserUid();
		Course course = getCourse();
		return getCourseBean().sectionManager.getSectionEnrollments(userUid, course.getUuid());
	}

	protected String getCategoryName(String categoryId) {
		return getCourseBean().sectionManager.getCategoryName(categoryId, JsfUtil.getLocale());
	}
	
	protected List getSectionCategories() {
		return getCourseBean().sectionManager.getSectionCategories(getSiteContext());
	}

	public boolean isInstructorFeaturesEnabled() {
		return getSiteRole().isInstructor();
	}
	
	public PreferencesBean getPrefs() {
		return getCourseBean().getPrefs();
	}
}



/**********************************************************************************
 * $Id$
 *********************************************************************************/
