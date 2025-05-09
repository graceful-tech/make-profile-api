<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <style>
     
	@page {
     size: A4;
     margin: 50px 30px 30px 30px;
     width:100%;	 
     }
	
 
    html, body {
      margin: 0;
      padding: 0;
      width: 210mm;
      height: 297mm;
      font-family: Arial, sans-serif;
      font-size: 11pt;
      color: #111;
    }

    .container {
      width: 200mm;
      padding:10px;
      margin: auto;
      background: white;
      box-sizing: border-box;
    }

    h1 {
      font-size: 20pt;
      margin: 0;
      font-weight: bold;
    }

    h2 {
      font-family: 'Helvetica', 'Arial', sans-serif;
      font-size: 12pt;
      margin: 5px 0 10px;
      color: #1a75cf;
      font-weight: normal;
    }

    .contact {
      margin-top: 8px;
      font-size: 9.5pt;
    }

    .contact span {
      margin-right: 15px;
    }

    .section {
      margin-top: 13px;
      margin-bottom: 0px;
     }

    .section-title {
      font-weight: bold;
      font-size: 11.5pt;
      margin-bottom: 5px;
      border-bottom: 1px solid #000;
      padding-bottom: 3px;
    }

    .job-title {
      font-weight: bold;
      font-size: 11pt;
      margin-top: 15px;
      margin-bottom: 3px;
    }

    .company {
      color: #1a75cf;
      font-weight: bold;
      margin-bottom: 4px;
    }

    .meta {
      font-size: 9.5pt;
      margin-bottom: 6px;
    }

    ul {
      margin: 0;
      padding-left: 20px;
    }

    ul li {
      margin-bottom: 6px;
      line-height: 1;
    }

    p {
      line-height: 1.2;
    }

    .skills, .certs {
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
    }

    .skills div, .certs div {
      
      font-size: 10pt;
	  flex: 1 1 45%;
      min-width: 150px;
      word-break: break-word;
    }

    .label {
      font-weight: bold;
      margin-top: 8px;
      
    }

    a {
      color: #1a75cf;
      text-decoration: none;
      font-size:11pt;
    }	

    .skill-container {
      font-size: 10pt;
      margin-bottom: 10px;
    }

    .skill-badge {
      display: inline-block;
      font-weight: bold;
      color: #333;
      border-bottom: 2px solid #ccc;
      padding: 2px 6px;
      margin: 4px 6px 4px 0;
    }

    .stack-title {
      color: #1a75cf;
      font-size: 10.5pt;
      margin: 10px 0 4px;
    }
    
	*, *::before, *::after {
     box-sizing: border-box;
    }
    
    .project {
	  margin-top: 18px;
      margin-bottom: 20px;
	  margin-left:15px;
	}
	
	.project-title {
	  font-weight: bold;
      font-size: 11.5pt;
      margin-bottom: 5px;
      padding-bottom: 5px;
	  color: #1a75cf;
	  border-bottom: 1px solid #ccc;
	}
	
	.project-name {
	  font-weight: bold;
      font-size: 11.5pt;
	  margin-bottom: 2px;
      padding-bottom: 2px;
	}
	
	.project-role { 
	 font-weight: bold;
      font-size: 11.5pt;
	  
	}
	
	.project-description{
	  padding-top: 10px;
	 }
	 
	.certs {
	  margin-top:10px;
	 }

  </style>
</head>

<body>

 
     

  <div class="container">
    <h1>${name}</h1>
    <h2>Junior Full Stack Developer</h2>
    <div class="contact">
      <span>${phone}</span>
      <span>${email}</span>
	  
	  <#if linkedin?has_content>
      <span>${linkedin}</span>
	  </#if> 
    </div>

 <#if objective?has_content>
    <div class="section">
      <div class="section-title">OBJECTIVE</div>
      <p>${objective}</p>
    </div>
 </#if> 	
	

 <#if summary?has_content>
    <div class="section">
      <div class="section-title">SUMMARY</div>
      <p>${summary}</p>
    </div>
 </#if> 	
	

 <#if experiences?? && experiences?size gt 0>
  <div class="section">
    <div class="section-title">EXPERIENCE</div>
    <#list experiences as experience>
      <div class="job-title">${experience.role}</div>
      <div class="company">${experience.companyName}</div>
      <div class="meta">${experience.experienceYearStartDate}
        <#if experience.experienceYearEndDate?has_content>
          - ${experience.experienceYearEndDate}
        <#else>
          - Present
        </#if>
      </div>

      <#if experience.responsibilities?? && experience.responsibilities?trim?length gt 0>
        <ul>
          <#list experience.responsibilities?split(",") as item>
            <li>${item?trim}</li>
          </#list>
        </ul>
      </#if>

      <#if experience.projects?? && experience.projects?size gt 0>
        <div class="project">
          <div class="project-title">PROJECT</div>
          <#list experience.projects as proj>
            <div class="project-name">Project Name: ${proj.projectName}</div>
            <div class="project-role">Project Role: ${proj.projectRole}</div>
            <div class="project-descriptoin">
              <ul>
                <li>${proj.projectDescription}</li>
              </ul>
            </div>
          </#list>
        </div>
      </#if>
    </#list>
  </div>
</#if>

 
  <#if education?? && education?size gt 0>   
     <div class="section">
      <div class="section-title">EDUCATION</div>
	 <#list education as edu> 
       <div class="job-title">${edu.department}</div>
       <div class="company">${edu.instutionName}</div>
       <div class="meta">${edu.qualificationStartYear} 
                        <#if edu.qualificationEndYear?has_content>
                                      ${edu.qualificationEndYear}
                         <#else>
                                 Present </#if>
	  </div>
	 </#list> 
    </div>
    
   </#if> 
   
   <#if skills?? && skills?trim?length gt 0> 
    <div class="section">
      <div class="section-title">SKILLS</div>
      <div class="skill-container">
	   <#list skills?split(",") as skill>
        <div class="skill-badge">${skill?trim}</div>
	    </#list>	
      </div>
    </div>
  </#if>
  
  <#if softSkills?? && softSkills?trim?length gt 0> 
    <div class="section">
      <div class="section-title">SOFT SKILLS</div>
      <div class="skill-container">
	   <#list softSkills?split(",") as skill>
        <div class="skill-badge">${skill?trim}</div>
	    </#list>	
      </div>
    </div>
  </#if>
  
  <#if competencies?? && competencies?trim?length gt 0> 
    <div class="section">
      <div class="section-title">CORE COMPETENCIES</div>
      <div class="skill-container">
	   <#list competencies?split(",") as com>
        <div class="skill-badge">${com?trim}</div>
	    </#list>	
      </div>
    </div>
  </#if>
  
  <#if certificates?? && certificates?size gt 0>
    <div class="section">
      <div class="section-title">CERTIFICATIONS</div>
	 <#list certificates as certificate>  
       <div class="certs">
        <div><strong>${certificate.name}</strong></div>
        <div><strong>${certificate.year}</strong></div>
       </div>
	 </#list> 
    </div>	
  </#if>
	
  </div>
</body>
</html>
