<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Resume - ${name}</title>
    <style>
        @page {
            size: A4;
            margin: 0;
        }

        html, body {
            margin: 0;
            padding: 0;
            width: 210mm;
            font-family: Arial, sans-serif;
            font-size: 14px;
            box-sizing: border-box;
            background: white;
        }

        .container {
            width: 200mm;
            height: 277mm;
            padding: 10mm;
            margin: auto;
            background: white;
            box-sizing: border-box;
            page-break-inside: avoid;
        }

        .header {
            margin-bottom: 6px;
        }

        .contact-info {
            font-size: 13px;
            color: black;
			font-style:bold;
        }

        h1 {
            margin: 0;
            font-size: 25px;
        }

        h2 {
            font-size: 15px;
            color: #003366;
            border-bottom: 1px solid #003366;
            margin-top: 8px;
            margin-bottom: 4px;
        }

        h3 {
            margin: 4px 0;
            font-size: 12px;
        }

        p, ul {
            margin: 3px 0;
            line-height: 1.5;
        }

        ul {
            padding-left: 15px;
        }

        ul li {
            margin: 2px 0;
        }

        .content-wrapper {
            display: table;
            width: 100%;
        }

        .left-column {
            display: table-cell;
            width: 65%;
            padding-right: 5mm;
            vertical-align: top;
        }

        .right-column {
            display: table-cell;
            width: 35%;
            padding-left: 5mm;
            vertical-align: top;
        }

        .section {
            margin-bottom: 6px;
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
        <div class="header">
            <h1>${name}</h1>
            <p class="contact-info">Phone: ${phone} | Mail: ${email} | 
           
           <#if linkedin?has_content>
            LinkedIn: ${linkedin}
           </#if> 
            </p>
        </div>

    <#if objective?has_content>
        <div class="section">
            <h2>Career Objective</h2>
            <p>${objective}</p>
        </div>
     </#if> 
     
     
        <div class="content-wrapper">
            <div class="left-column">
       <#if summary?has_content>
                <div class="section">
                    <h2>Professional Summary</h2>
                    <p>${summary}</p>
                </div>
       </#if>           

           <#if experiences?? && experiences?size gt 0>
                <div class="section">
                    <h2>Work Experience</h2>
                    <#list experiences as experience>
                    
                     <#if experience.role?has_content>
                        <h3><strong>Role Name: </strong>${experience.role}</h3>
                     </#if>
                      
                     <#if experience.companyName?has_content ||  experience.experienceYearStartDate?has_content>  
                       
                      <p>
                       <#if experience.companyName?has_content>
                          ${experience.companyName} 
                        </#if>   
                        
                     <#if experience.experienceYearStartDate?? && experience.experienceYearStartDate?has_content>    
                        (${extractYear(experience.experienceYearStartDate)} -
                        <#if experience.experienceYearEndDate?? && experience.experienceYearEndDate?has_content> 
                         ${extractYear(experience.experienceYearEndDate)}
                      <#else> 
                          Present </#if>)
                      </#if>     
                     
                       </p>
                    </#if>  
                     
                      <#if  experience?? && experience.responsibilities?? && experience.responsibilities?trim?length gt 0>
                        <p><strong>Responsibilities: </strong></p>
                        <ul>
                            <#list experience.responsibilities?split(",") as item>
                               <#if item?has_content>
                                  <li>${item?trim}</li>
                               </#if>  
                            </#list>
                        </ul>
                       </#if>  
                    </#list>
                </div>
            </#if> 
            
      </div>  
            <div class="right-column">
            
            <#if skills?? && skills?trim?length gt 0>
                <div class="section">
                    <h2>Technical Skills</h2>
                    <ul>
                        <#list skills?split(",") as skill>
                          <#if skill?has_content>
                             <li>${skill?trim}</li>
                          </#if>  
                        </#list>
                    </ul>
                </div>
            </#if>   
            
         <#if softSkills?? && softSkills?trim?length gt 0>
                <div class="section">
                    <h2>Soft Skills</h2>
                    <ul>
                        <#list softSkills?split(",") as skill>
                           <#if skill?has_content>
                              <li>${skill?trim}</li>
                           </#if>   
                        </#list>
                    </ul>
                </div>
           </#if>
           
          <#if competencies?? && competencies?trim?length gt 0>
                <div class="section">
                    <h2>Core Competencies</h2>
                    <ul>
                        <#list competencies?split(",") as comp>
                            <#if comp?has_content>
                               <li>${comp?trim}</li>
                            </#if>
                        </#list>
                    </ul>
                </div>
           </#if> 
              
            </div>
        </div>
        
            <#if education?? && education?size gt 0>
                <div class="section">
                    <h2>Education</h2>
                    <#list education as edu>
                    
                    <#if edu.department?has_content || edu.institutionName?has_content>
                        <p>
                        
                        <#if edu.department?has_content>
                           <strong>Degree: ${edu.department}</strong><br />
                        </#if>
                     
	                    <#if edu.institutionName?has_content>  
	                         <strong>Institution Name: </strong>${edu.institutionName} 
	                     </#if>
                        
                        <#if edu.qualificationStartYear?? && edu.qualificationStartYear?has_content>
                            (${extractYear(edu.qualificationStartYear)}
                        <#if edu.qualificationEndYear?? && edu.qualificationEndYear?has_content>
                            - ${extractYear(edu.qualificationEndYear)}
                            <#else>
                                - Present
                        </#if>)
                           </#if> 
                         </p>
                      </#if>		   
                    </#list>
                </div>
          </#if>
          
          <#assign hasProjects = false>
					<#if experiences?? && experiences?size gt 0>
					    <#list experiences as exp>
					       <#if exp.projects?? && exp.projects?size gt 0>
					          <#assign hasProjects = true>
					        <#break>
					    </#if>
					  </#list>
					</#if>
          
         <#if hasProjects>
             <div class="section">
                 <h2>Projects</h2>
         <#list experiences as exp>
            <#if exp.projects?? && exp.projects?size gt 0>
                <#list exp.projects as proj>
                
                <#if proj.projectName?has_content>
                    <h3><strong>Project Name:</strong> ${proj.projectName}</h3>
                 </#if>
                 
                 <#if proj.projectRole?has_content>   
                    <h3><strong>Project Role:</strong> ${proj.projectRole}</h3>
                  </#if> 
                   
                  <#if proj.projectDescription?has_content>
                    <p><strong>Project Description:</strong> ${proj.projectDescription}</p>
                    </#if>
                </#list>
            </#if>
          </#list>
         </div>
        </#if>
        
    </div>
</body>
</html>
