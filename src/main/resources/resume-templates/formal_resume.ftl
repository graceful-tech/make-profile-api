<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${name}'s Resume</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .container {
            width: 210mm; /* A4 width */
            height: 297mm; /* A4 height */
            background: #fff;
            padding: 20mm;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
        }
        .header {
            text-align: left;
            font-size: 18px;
            font-weight: bold;
        }
        .section {
            display: flex;
            flex-direction: column;
            margin-top: 5px;  
        }
        .section-title {
            font-size: 18px;
            font-weight: bold;
            border-bottom: 2px solid #000;
            margin-bottom: 3px;
        }
        .content {
            margin-top: 3px;
			padding:10px;
        }
        .content ul {
            padding-left: 20px;
        }
        .sub-heading{
            font-size: 13px;
            font-weight: bold;
            border-bottom: 2px solid #000;
            margin-bottom: 3px;
        }
        /* Print styles for A4 */
        @media print {
            body {
                background-color: #fff;
            }
            .container {
                box-shadow: none;
                margin: 0;
                padding: 0;
            }
        }
    </style>
</head>
<body>


    <div class="container">
        <div class="header">
            ${name}  <br />
            Phone: ${phone} | Email: ${email}  <br />
            <#if summary?has_content>
               LinkedIn: <a href="${linkedin}">LinkedIn Profile</a>
                <br />
           </#if> 
        </div>
      
     <#if summary?has_content>
        <div class="section">
            <div class="section-title">Profile Summary</div>
            <div class="content">${summary}</div>
        </div>
     </#if>   
        
    
  <#if experiences?size gt 0>
     <div class="section">
        <div class="section-title">Work Experience</div>
        <div class="content">
            <#list experiences as experience>
                <b>${experience.role}, ${experience.companyName} (${experience.experienceYearStartDate} - 
                <#if experience.experienceYearEndDate?has_content>
                ${experience.experienceYearEndDate})
                <#else>
                  Present
                </#if> 
                
                </b>
    
                <#if experience.projects?size gt 0>
                    <div class="section" style="padding-left:15px;">
                        <div class="sub-heading">Projects</div>
                        <div class="content">
                            <#list experience.projects as project>
                             
                               <div>Project Name : ${project.projectName}</div>
                               <div>Project Role : ${project.projectRole}</div>
                                
                                <ul>
                                <div Style="padding-top:5px;">Skills</div>
                                    <#list project.projectSkills as skill>
                                        <li>${skill}</li>
                                    </#list>
                                </ul>
                                <div class="content">${project.projectDescription}</div>
                            </#list>
                        </div>
                    </div>
                </#if>
            </#list>
        </div>
    </div>
 </#if>
 

   
   <#if skills?size gt 0>
        <div class="section">
            <div class="section-title">Skills</div>
            <div class="content">
                <ul>
                    <#list skills as skill>
                        <li>${skill}</li>
                    </#list>
                </ul>
            </div>
        </div>
    </#if> 

   
    <div class="section">
        <div class="section-title">Personal Information</div>
        <div class="content">
            <ul>
                <li><b>Date of Birth:</b> ${dob}</li>
                <li><b>Address:</b> ${address}</li>
            </ul>
        </div>
    </div>

     
  
    </div>
    
    
</body>
</html>
