<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Resume - John Doe</title>
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
            height: 297mm;
            font-family:  Arial, sans-serif;
            font-size: 10pt;
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
            font-size: 10.5pt;
			color:#007bff;
			font-family: "Times New Roman", Times, serif;
			font-weight:bold;
			font-style:bold;
        }
        
		
		.content-title {
		    font-family: "Times New Roman", Times, serif;
            font-size: 12pt;
            color:  #0000ff;
            border-bottom: 1px solid #003366;
            margin-top: 8px;
			font-weight:bold;
		}

        p, ul {
            margin: 3px 0;
            line-height: 1.5;
			margin-top: 8px;
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
            width: 33%;
            padding-right: 5mm;
            vertical-align: top;
        }

        .right-column {
            display: table-cell;
           width: 33%;
            padding-left: 5mm;
            vertical-align: top;
        }
		
		.center-column{
		    display: table-cell;
            width: 33%;
            padding-left: 5mm;
            vertical-align: top;
		}

        .section {
            margin-bottom: 6px;
        }
		 
		.skills-text {
         font-size: 10pt;
         }
		 
		 }
		 .certificates{
		   margin-top:20px;
		   padding:10px;
		 }
	    
		skill-container {
		   font-size: 10pt;
		    margin-bottom: 10px;
	    }

	    .skill-badge {
		  display: inline-block;
		  font-weight: bold;
		  color: #333;
		  border-bottom: 2px solid #ccc;
		  padding: 2px 6px;
		  margin: 5px 6px 4px 0;
	    }
	    .stack-title {
	     color: #1a75cf;
	     font-size: 10.5pt;
	     margin: 10px 0 4px;
	    }
	
	    .achievements{
	     margin-top:10px;
	    }
		

    </style>
</head>
<body>

    <div class="container">
         <div class="header">
            <h1>${name}</h1>
            
			 <#if roleName?has_content>
			  <h3>Full Stack Developer</h3>
			</#if> 
			
            <p class="contact-info">Phone: ${phone} | Mail: ${email} |
           
            <#if linkedin?has_content>
            LinkedIn: linkedin.com/in/johndoe</p>
            </#if> 
        </div>

       

        <div class="content-wrapper">
        
            <div class="left-column">
			
			<#if objective?has_content>
			    <div class="section">
                      <div class="content-title">Career Objective</div>
                           <p>${objective}</p>
                 </div>
            </#if>     
                
                
			<#if summary?has_content>
                <div class="section">
				      <div class="content-title">Professional Summary</div>
				      <p>${summary}</p>
                 </div>
            </#if>    

             <#if skills?? && skills?trim?length gt 0>
                  <div class="section">
                     <div class="content-title">Skills</div>
                         <div class="skill-container">
                    <#list skills?split(",") as skill>
					     <div class="skill-badge">${skill?trim}</div>
					 </#list>   
				      </div>
                  </div>
			  </#if>
			 
			 
             <#if softSkills?? && softSkills?trim?length gt 0> 
					<div class="section">
						 <div class="section-title">Soft Skills</div>
						    <div class="skill-container">
						         <#list softSkills?split(",") as skill>
						            <div class="skill-badge">${skill?trim}</div>
						         </#list>	
						  </div>
				     </div>
             </#if>
		
            </div>

       <div class="center-column">
			
			<#if experiences?? && experiences?size gt 0>
			     <div class="section">
			        <div class="content-title">Work Experience</div>
			        <#list experiences as experience>
                         <h3>${experience.role}</h3>
                              <p><strong>${experience.companyName} ${experience.experienceYearStartDate} -
                                  <#if experience.experienceYearEndDate?has_content> ${experience.experienceYearEndDate}
                                   <#else> Present </#if></strong> </p>
                                 
                            <#if  experience?? && experience.responsibilities?? && experience.responsibilities?trim?length gt 0>     
                                 <ul>
                                     <#list experience.responsibilities?split(",") as item>
                                            <li>${item?trim}</li>
                                     </#list>
                                 </ul>
                             </#if>      
                     </#list>            
                 </div>
            </#if>   
                 
                 <#if education?? && education?size gt 0>   
				          <div class="section">
				               <div class="content-title">Education</div>
				               <#list education as edu>
                                      <p><strong>${edu.department}</strong><br />
                                      ${edu.instutionName} ${edu.qualificationStartYear} 
                                            <#if edu.qualificationEndYear?has_content>
                                         ${edu.qualificationEndYear}
                                              <#else>
                                                 Present </#if></p>
                                </#list>       
                                </div>
                           </div>
                  </#if>         
                           
			
		<div class="right-column">
		
		
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
					       <div class="content-title">Projects</div>
					         <#list experiences as exp> 
					           <#if exp.projects?? && exp.projects?size gt 0>
					              <#list exp.projects as proj>
					                  <p><strong>Project Name:</strong> ${proj.name}</p>
					                  <p><strong>Project Role:</strong> ${proj.role}</p>
					                  <p><strong>Project Description:</strong> ${proj.description}</p>
					              </#list> 
					          </#if>   
					       </#list>                
					  </div>
			   </#if>
			   
			   
			  <#if achievements?? && achievements?size gt 0>   
				  <div class="section">
                      <div class="content-title">Achievements & Awards</div>
                       <#list achievements as achieve>
                          <div class="achievements"><strong><a>${achieve.name}</a></strong><br />${proj.year}</div>
                        </#list> 
                  </div>
              </#if>   
			
			<#if certificates?? && certificates?size gt 0>  
			     <div class="section">
                     <div class="content-title">Certificates</div>
                        <#list certificates as certifi>
                          <div class="achievements"><strong><a>${certifi.name}</a></strong><br />${certifi.year}</div>
                        </#list>
                 </div>
             </#if>      
               
             <#if competencies?? && competencies?trim?length gt 0>     
				 <div class="section">
                     <div class="content-title">Core Compentencies</div>
                         <ul>
                         <#list competencies?split(",") as comp>
							<li>${comp?trim}</li>
					      </#list>		
                         </ul>
                </div>
            </#if>     
            </div>
        </div>
    </div>
</body>
</html>
