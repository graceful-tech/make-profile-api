<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <style>
     
	 @page {
	  size: A4;
	  margin: 30px 30px; 
	  width: 100%; 
	 }
	
    html, body {
      margin: 0;
      padding: 0;
      width: 210mm;
      font-family: Arial, sans-serif;
      font-size: 11pt;
      color: #111;
    }

    .container {
      width: 200mm;
      padding: 10mm;
      margin: auto;
      background: white;
      box-sizing: border-box;
      position: relative;
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
      color:#1a75cf;
    }

    .job-title {
      font-weight: bold;
      font-size: 11pt;
      margin-top: 10px;
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
      line-height: 1.3;
    }

    p {
      line-height: 1.3;
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
	 
	 .skills-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
  }

  .skills-table td {
    padding-left: 20px;
    padding-bottom: 8px;
    font-weight: 500;
    position: relative;
    vertical-align: top;
  }

  .skills-table td::before {
    content: '•';
    position: absolute;
    left: 0;
    color: #000;  
    font-size: 18px;
    line-height: 1;
  }
  
  .certificate{
     line-height: 1.8;
     margin-top:3px;
     
  }
 
	
	 

  </style>
</head>

<body>

<#function extractYear input>
  <#if input?is_date>
    <#return input?string("yyyy")>
  <#elseif input?? && input?has_content>
    <#-- Try parsing known formats -->
    <#attempt>
      <#-- Try dd/MM/yyyy -->
      <#local parsedDate = input?date("dd/MM/yyyy")>
      <#return parsedDate?string("yyyy")>
    <#recover>
      <#attempt>
        <#-- Try yyyy-MM-dd -->
        <#local parsedDate = input?date("yyyy-MM-dd")>
        <#return parsedDate?string("yyyy")>
      <#recover>
        <#-- Return blank if parsing fails -->
        <#return "">
      </#attempt>
    </#recover>
  <#else>
    <#return "">
  </#if>
</#function>
 
     

  <div class="container">
    <h1>${name}</h1>
     <div class="contact">
      <span>Phone: ${phone}</span>
      <span>| Mail: ${email}</span>
	  
	  <#if linkedin?has_content>
      <span>LinkedIn: ${linkedin}</span>
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
  
      <#if experience.role?has_content> 
         <div class="job-title">Role: ${experience.role}</div>
       </#if>
      
      <#if experience.role?has_content> 
        <div class="company">Company: ${experience.companyName}</div>
      </#if>
      
      <#if experience.experienceYearStartDate?? && experience.experienceYearStartDate?has_content> 
         <div class="meta">
          ( ${extractYear(experience.experienceYearStartDate)}
           <#if experience.experienceYearEndDate?? && experience.experienceYearEndDate?has_content>
          - ${extractYear(experience.experienceYearEndDate)} )
           <#else>
             - Present )
          </#if>
         </div>
     </#if>
     
      <#if experience.responsibilities?? && experience.responsibilities?trim?length gt 0>
        <ul>
          <#list experience.responsibilities?split(",") as item>
            <#if item?has_content>
              <li>${item?trim}</li>
            </#if>
          </#list>
        </ul>
      </#if>

      <#if experience.projects?? && experience.projects?size gt 0>
		  <div class="project">
		    <div class="project-title">PROJECT</div>
		    <#list experience.projects as proj>
		      
		      <#if proj.projectName?? && proj.projectName?has_content>
		        <div class="project-name">Project Name: ${proj.projectName}</div>
		      </#if>
		      
		      <#if proj.projectRole?? && proj.projectRole?has_content>
		        <div class="project-role">Project Role: ${proj.projectRole}</div>
		      </#if>
		      
		      <#if proj.projectDescription?? && proj.projectDescription?has_content>
		        <div class="project-description">
		          <ul>
		            <li>${proj.projectDescription}</li>
		          </ul>
		        </div>
		      </#if>
		      
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
       
      <#if edu.department?has_content> 
        <div class="job-title">Department: ${edu.department}</div>
      </#if>
       
      <#if edu.institutionName?has_content>
          <div class="company">Institution: ${edu.institutionName}</div>
      </#if>    
       
      <#if edu.qualificationStartYear?? && edu.qualificationStartYear?has_content>
          <div class="meta">
 	            
             ( ${extractYear(edu.qualificationStartYear)} 
                        
               <#if edu.qualificationEndYear?? && edu.qualificationEndYear?has_content>
                   -  ${extractYear(edu.qualificationEndYear)} )
                <#else>
                    - Present ) </#if>             
	      </div>
	  </#if>  
	     
	 </#list> 
    </div>
    
   </#if> 
   
		<#if skills?? && skills?trim?length gt 0>
		  <div class="section">
		    <div class="section-title">SKILLS</div>
		    <table class="skills-table">
		      <#assign skillList = skills?split(",")>
		      <#assign i = 0>
		      <#list skillList as skill>
		        <#if i % 2 == 0>
		          <tr>
		        </#if>
		        <td><#if skill?has_content> ${skill?trim}</#if></td>
		        <#if i % 2 == 1>
		          </tr>
		        </#if>
		        <#assign i = i + 1>
		      </#list>
		      <#-- If odd number of items, close the last <tr> -->
		      <#if (i % 2) != 0>
		        <td></td></tr>
		      </#if>
		    </table>
		  </div>
		</#if>


		<#if competencies?? && competencies?trim?length gt 0>
		  <div class="section">
		    <div class="section-title">CORE COMPETENCIES</div>
		    <table class="skills-table">
		      <#assign skillList = competencies?split(",")>
		      <#assign i = 0>
		      <#list skillList as skill>
		        <#if i % 2 == 0>
		          <tr>
		        </#if>
		        <td><#if skill?? && skill?has_content> ${skill?trim}</#if></td>
		        <#if i % 2 == 1>
		          </tr>
		        </#if>
		        <#assign i = i + 1>
		      </#list>
		      <#-- If odd number of items, close the last <tr> -->
		      <#if (i % 2) != 0>
		        <td></td></tr>
		      </#if>
		    </table>
		  </div>
		</#if>


  

  <#if certificates?? && certificates?size gt 0>
    <div class="section">
      <div class="section-title">CERTIFICATES</div>
	 <#list certificates as certificate>  
       
        <#if certificate.courseName?has_content>
           <div class="certificate"><strong>${certificate.courseName}</strong>
           
            <#if certificate.courseStartDate?? && certificate.courseStartDate?has_content>
              <span style="padding-left:10px;">  ( ${extractYear(certificate.courseStartDate)}   </span>
                   
                    <#if certificate.courseEndDate?? && certificate.courseEndDate?has_content>
                        <span style="padding-left:3px;">  - ${extractYear(certificate.courseEndDate)} )  </span>
                         <#else>
                             )
                    </#if> 
                      
             </#if>
           
           
           </div>
        </#if> 
        
        
	 </#list> 
    </div>	
  </#if>
  
  <#if softSkills?? && softSkills?trim?length gt 0>
		  <div class="section">
		    <div class="section-title">SOFT SKILLS</div>
		    <table class="skills-table">
		      <#assign skillList = softSkills?split(",")>
		      <#assign i = 0>
		      <#list skillList as skill>
		        <#if i % 2 == 0>
		          <tr>
		        </#if>
		        <td><#if skill?has_content> ${skill?trim}</#if></td>
		        <#if i % 2 == 1>
		          </tr>
		        </#if>
		        <#assign i = i + 1>
		      </#list>
		      <#-- If odd number of items, close the last <tr> -->
		      <#if (i % 2) != 0>
		        <td></td></tr>
		      </#if>
		    </table>
		  </div>
		</#if>
   
	
  </div>
</body>
</html>
