<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
     <style>
        @page {
            size: A4;
            margin: 30px;
            width:100%;
        }

        html, body {
            margin: 0;
            padding: 0;
            width: 210mm;
            font-family:  Arial, sans-serif;
            font-size: 14px;
            box-sizing: border-box;
            background: white;
        }

        .container {
            width: 200mm;
            height: 277mm;
            padding: 5mm;
            margin: auto;
            background: white;
            box-sizing: border-box;
            page-break-inside: avoid;
        }

        .header {
            margin-bottom: 6px;
        }

        .contact-info {
            font-size: 15px;
            color: black;
			font-weight:bold;
			font-family: "Times New Roman", Times, serif;
        }

        h1 {
            margin: 0;
            font-size: 22px;
			font-family: "Times New Roman", Times, serif;
        }

        h2 {
		    font-family: "Times New Roman", Times, serif;
            font-size: 18px;
            color:  #0000ff;
            border-bottom: 1px solid #003366;
            margin-top: 8px;
            margin-top: 4px;
        }

        h3 {
            margin: 4px 0;
            font-size: 15px;
			color:#1a75cf;
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
            width: 40%;
            padding-right: 5mm;
            vertical-align: top;
        }

        .right-column {
            display: table-cell;
            width: 60%;
            padding-left: 5mm;
            vertical-align: top;
        }

        .section {
            margin-bottom: 6px;
        }
		 
		.skills-text {
         font-size: 15px;
         
         }
		 .certificates{
		    margin-top:10px;
		 
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
            LinkedIn: ${linkedin}</p>
            </#if> 
               
               </p>
        </div>

       

      <div class="content-wrapper">
           <div class="left-column">
			
			<#if objective?has_content>
			     <div class="section">
                    <h2>Career Objective</h2>
                        <p>${objective}</p>
                </div>
			</#if>
			
			
			<#if summary?has_content>
                 <div class="section">
                     <h2>Professional Summary</h2>
                         <p>${summary}</p>
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
                                     <p><strong>Project Name:</strong>${proj.projectName}</p>
                                </#if>
                                <#if proj.projectRole?has_content>  
                                    <p><strong>Project Role:</strong>${proj.projectRole}</p>
                                </#if>    
                                <#if proj.projectName?has_content>     
                                    <p><strong>Project Description:</strong>${proj.projectDescription}</p>
                                </#if>  
                                    
                               </#list> 
                          </#if>   
                     </#list>                
                 </div>
           </#if>  
           
           
           <#if certificates?? && certificates?size gt 0>
                <div class="section">
                    <h2>Certificates</h2>
                        <#list certificates as certificate> 
                            <div class="certificates">
                            
                            <strong><a>${certificate.courseName}</a></strong>
                            &nbsp;&nbsp;
                       <#if certificate.courseStartDate?? && certificate.courseStartDate?has_content>    
                            ${extractYear(certificate.courseStartDate)}</div>
                           </#if> 
                            
                        </#list>    
                 </div>  
           </#if>        
			  
       </div>

        <div class="right-column">
            
			<#if experiences?? && experiences?size gt 0>	
				 <div class="section">
                      <h2>Work Experience</h2>
                   <#list experiences as experience>
                        <h3>Role: ${experience.role}</h3>
                        
                        
                           <p><strong>
                           <#if experience.companyName?has_content>
                              Company: ${experience.companyName}
                           </#if> 
                              <#if experience.experienceYearStartDate?? && experience.experienceYearStartDate?has_content> 
                                  ${extractYear(experience.experienceYearStartDate)} -
                            
                                 <#if experience.experienceYearEndDate?? && experience.experienceYearEndDate?has_content> 
                                      ${extractYear(experience.experienceYearEndDate)}
                                         <#else> Present </#if>
                               
                               </#if>  
                            </strong> 
                            </p>
                             
                           <#if  experience?? && experience.responsibilities?? && experience.responsibilities?trim?length gt 0>
                              <ul>
                                  <#list  experience.responsibilities?split(",") as item>
                                     <#if item?has_content> 
                                        <li>${item?trim}</li>
                                      </#if>   
                                   </#list>
                              </ul>
                           </#if>  
                    </#list>        
                  </div>
			</#if>
				
			
			<#if education?? && education?size gt 0> 	
				 <div class="section">
                     <h2>Education</h2>
                          <#list education as edu> 
                          
                          <#if edu.department?has_content || edu.instutionName?has_content>
                             <p>
                               <#if edu.department?has_content>
                                   <strong>${edu.department}</strong><br />
                                </#if> 
                             
                             <#if edu.instutionName?has_content>
                              ${edu.instutionName} 
                             </#if> 
                              
                             <#if edu.qualificationStartYear?? && edu.qualificationStartYear?has_content>  
                               (${extractYear(edu.qualificationStartYear)} -
                                  <#if edu.qualificationEndYear?? && edu.qualificationEndYear?has_content>
                                      ${extractYear(edu.qualificationEndYear)}
                                       <#else>
                                        Present </#if>)
                              </#if>  
                              </p>
                           </#if>  
                              
                          </#list>    
                 </div>
            </#if>     
				
		
		    <#if skills?? && skills?trim?length gt 0> 		 
				  <div class="section">
                       <h2 >Skills</h2>
                          <p class="skills-text">
                             <#list skills?split(",") as skill>${skill?trim}<#if skill_has_next>, </#if></#list>
                           </p>
                   </div>
			 </#if> 
                 
              <#if softSkills?? && softSkills?trim?length gt 0> 
                   <div class="section">
                        <h2>Soft Skills</h2>
                      <p class="skills-text"> 
                         <#list softSkills?split(",") as skill>${skill?trim}<#if skill_has_next>, </#if></#list>
                      </p>
                   </div>
               </#if>    

             <#if competencies?? && competencies?trim?length gt 0> 
                  <div class="section">
                       <h2>Core Competencies</h2>
                           <p class="skills-text"> 
                              <#list competencies?split(",") as com>${com?trim}<#if com_has_next>, </#if></#list>
                           </p>
                  </div>
		      </#if>
 				 
            </div>
        </div>

     </div>
</body>
</html>
