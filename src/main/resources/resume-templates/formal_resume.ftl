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
            margin-top: 5px; /* Reduced gap between sections */
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
                    <b>${experience.role}, ${experience.company} (${experience.duration})</b>
                    <ul>
                        <#list experience.responsibilities as responsibility>
                            <li>${responsibility}</li>
                        </#list>
                    </ul>
                </#list>
            </div>
        </div>
   </#if> 

   <#if projects?size gt 0>
        <div class="section">
            <div class="section-title">Projects</div>
            <div class="content">
                <#list projects as project>
                    <b>${project.name}</b>
                    <ul>
                        <#list project.details as detail>
                            <li>${detail}</li>
                        </#list>
                    </ul>
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

   <#if certifications?size gt 0>
        <div class="section">
            <div class="section-title">Certifications</div>
            <div class="flex-container">
                <#list certifications as certification>
                    <div class="flex-item">${certification}</div>
                </#list>
            </div>
        </div>
   </#if>
   
   
   <#if education?size gt 0>
        <div class="section">
            <div class="section-title">Education</div>
            <div class="content">
                <#list education as edu>
                    <div>${edu.degree}, ${edu.institution} (${edu.year})</div>
                </#list>
            </div>
        </div>
    </#if>  

    <div class="section">
        <div class="section-title">Personal Information</div>
        <div class="content">
            <ul>
                <li><b>Date of Birth:</b> ${dob}</li>
                <li><b>Gender:</b> ${gender}</li>
                <li><b>Languages Known:</b> ${languages}</li>
            </ul>
        </div>
    </div>

    <#if softSkills?size gt 0>
        <div class="section">
            <div class="section-title">Soft Skills</div>
            <div class="flex-container">
                <#list softSkills as skill>
                    <div class="flex-item">${skill}</div>
                </#list>
            </div>
        </div>
    </#if> 
  
   <#if awards?has_content>
        <div class="section">
            <div class="section-title">Awards</div>
            <div class="content">${awards}</div>
        </div>
   </#if>   
     
    </div>
</body>
</html>
