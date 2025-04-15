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
            height: 297mm;
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
            font-size: 16px;
        }

        h2 {
            font-size: 12px;
            color: #003366;
            border-bottom: 1px solid #003366;
            margin-top: 8px;
            margin-bottom: 4px;
        }

        h3 {
            margin: 4px 0;
            font-size: 11px;
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
     
     <#if summary?has_content>
        <div class="content-wrapper">
            <div class="left-column">
                <div class="section">
                    <h2>Professional Summary</h2>
                    <p>${summary}</p>
                </div>
       </#if>           

           <#if experiences?? && experiences?size gt 0>
                <div class="section">
                    <h2>Work Experience</h2>
                    <#list experiences as experience>
                        <h3>${experience.role}</h3>
                        <p>${experience.companyName} (${experience.experienceYearStartDate} -
                     <#if experience.experienceYearEndDate?has_content> ${experience.experienceYearEndDate}
                     <#else> Present </#if>)
                      </p>
                      <#if  experience?? && experience.responsibilities?? && experience.responsibilities?size gt 0>
                        <ul>
                            <#list experience.responsibilities as item>
                                <li>${item}</li>
                            </#list>
                        </ul>
                       </#if>  
                    </#list>
                </div>
            </#if> 
            
      </div>  
            <div class="right-column">
                <div class="section">
                    <h2>Technical Skills</h2>
                    <ul>
                        <#list skills as skill>
                            <li>${skill}</li>
                        </#list>
                    </ul>
                </div>
            
         <#if softSkills?? && softSkills?size gt 0>
                <div class="section">
                    <h2>Soft Skills</h2>
                    <ul>
                        <#list softSkills as skill>
                            <li>${skill}</li>
                        </#list>
                    </ul>
                </div>
           </#if>
           
          <#if competencies?? && competencies?size gt 0>
                <div class="section">
                    <h2>Core Competencies</h2>
                    <ul>
                        <#list competencies as comp>
                            <li>${comp}</li>
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
                        <p><strong>${edu.department}</strong><br />${edu.instutionName} (${edu.qualificationStartYear} 
                        <#if edu.qualificationEndYear?has_content>
                                      ${edu.qualificationEndYear}
                                     
                             <#else>
                                 Present </#if>) </p>
                    </#list>
                </div>
          </#if>
          
         <#if experiences?? && experiences?size gt 0>
             <div class="section">
            <h2>Projects</h2>
         <#list experiences as exp>
            <#if exp.projects?? && exp.projects?size gt 0>
                <#list exp.projects as proj>
                    <h3><strong>Project Name:</strong> ${proj.projectName}</h3>
                    <h3><strong>Project Role:</strong> ${proj.projectRole}</h3>
                    <p><strong>Project Description:</strong> ${proj.projectDescription}</p>
                </#list>
            </#if>
          </#list>
         </div>
        </#if>
        
    </div>
</body>
</html>
