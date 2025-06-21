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

    .profile-pic {
      position: absolute;
      top: 9mm;
      right: 10mm;
      width: 30mm;
      height: 30mm;
      object-fit: cover;
      border-radius: 50%;
      border: 2px solid #0054a6;
    }
    
    p{
	  line-height:1.2;
	}

    h1 {
      font-size: 32px;
      font-weight: bold;
      margin-bottom: 0;
    }

    .blue {
      color: #0054a6;
      font-weight: bold;
    }

    .contact {
      font-size: 14px;
      margin-bottom: 20px;
    }

    h2 {
      font-size: 18px;
      border-bottom: 2px solid #222;
    }

    .section {
      margin-top: 18px;
      page-break-inside: avoid;
    }

    .section-title {
      font-weight: bold;
      font-size: 18px;
      border-bottom: 2px solid #000; 
      color: #0054a6;
    }

    .job-title {
      font-weight: bold;
    }

    .location-date {
      font-size: 14px;
      color: #555;
      margin-top: 10px;
    }

    .bullets {
      padding-left: 20px;
    }

    .bullets li {
      line-height:1.3;
    }

    .tags {
      margin-top: 10px;
    }

    .tag {
      display: inline-block;
      background-color: #eee;
      border-radius: 4px;
      padding: 4px 8px;
      margin: 4px 4px 0 0;
      font-size: 13px;
    }

    .skills-section {
    width: 100%;
  }

  .skill-item {
    display: table;
    width: 100%;
    margin-top: 6px;
  }

  .skill-name {
    display: table-cell;
    width: 40%;
    font-weight: bold;
    padding-right: 8px;
    vertical-align: middle;
  }

  .skill-dots {
    display: table-cell;
    width: 60%;
    vertical-align: middle;
  }

  .dot {
    display: inline-block;
    width: 9px;
    height: 9px;
    border-radius: 50%;
    background-color: #ccc;
    margin-right: 4px;
  }

  .dot.filled {
    background-color: #0054a6;
  }

    .timeline {
      position: relative;
      margin-top: 13px;
      padding-left: 20px;
      border-left: 3px dotted #0054a6;
    }

    .timeline-item {
      position: relative;
      padding-left: 20px;
    }

    .timeline-item::before {
      content: '';
      position: absolute;
      left: -11px;
      top: 5px;
      width: 14px;
      height: 14px;
      background-color: #0054a6;
      border-radius: 50%;
      border: 3px solid white;
      box-shadow: 0 0 0 4px #0054a6;
    }

    .timeline-date {
      font-weight: bold;
      color: #0054a6;
    }

    .timeline-content .job-title {
      font-weight: bold;
      font-size: 16px;
    }

    .timeline-content .company {
      font-size: 14px;
      margin-bottom: 8px;
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
 
	 <#if profileImage?? && profileImage?has_content>
	    <img src="${profileImage}" alt="Profile Image"   class="profile-pic" />
	 </#if>

      <h1>${name}</h1>
  
   <div class="contact">
   
	   <#if phone?has_content>
	       Phone: ${phone}
	    </#if>
	    
	    <#if email?has_content>
	          | Email: ${email}
	    </#if>
	    
	    <#if linkedin?? && linkedin?has_content>
	        | LinkedIn:  ${linkedin}
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
    <div class="section-title">PROFESSIONAL EXPERIENCE</div>
    <div class="timeline">

      <#list experiences as experience>
        <div class="timeline-item">
          <div class="timeline-date">
            <#if experience.experienceYearStartDate?? && experience.experienceYearStartDate?has_content> 	      
              ${extractYear(experience.experienceYearStartDate)} 
                    <#if experience.experienceYearEndDate?? && experience.experienceYearEndDate?has_content>
                         - ${extractYear(experience.experienceYearEndDate)}
                              <#else>
                                  - Present
                     </#if>	      
            </#if>	
          </div>

          <div class="timeline-content">
            <#if experience.role?has_content>
              <div class="job-title">${experience.role}</div>
            </#if>	

            <#if experience.companyName?has_content>
              <div class="company">${experience.companyName}</div>
            </#if>

            <#if experience.responsibilities?? && experience.responsibilities?trim?length gt 0>
              <ul class="bullets">
                <#list experience.responsibilities?split(",") as item>
                  <#if item?has_content>
                    <li>${item?trim}</li>
                  </#if>
                </#list>
              </ul>
            </#if>
          </div>
        </div>
      </#list> 
      
    </div>
  </div>
</#if>

  
 <#if education?? && education?size gt 0> 
	<div class="section">
		    <div class="section-title">EDUCATION</div>
 				    <ul class="bullets">
				    	<#list education as edu>
						 <li>
 						      <#if edu.department?has_content>  
							      <strong>
							         ${edu.department}
							       </strong> 
							    </#if>	    
 								<#if edu.institutionName?has_content>
	  						        – ${edu.institutionName}
	  						    </#if>   
   						            <#if edu.qualificationStartYear?? && edu.qualificationStartYear?has_content> 
			                                  ( ${extractYear(edu.qualificationStartYear)} 
			                                      <#if edu.qualificationEndYear?? && edu.qualificationEndYear?has_content>
                                                    - ${extractYear(edu.qualificationEndYear)} )
                                                      <#else>
                                                       - Present )</#if> 
			                           </#if> 
 						   </li>
  						</#list>     
				    </ul>
		    
		  </div>
		  
 </#if> 
    
	<#if skills?? && skills?trim?length gt 0>
	  <div class="section">
	    <div class="section-title">TECHNICAL SKILLS</div>
	    <div class="skills-section">
	      <#list skills?split(",") as skill>
	        <#if skill?? && skill?has_content>
	          <div class="skill-item">
	            <div class="skill-name">${skill?trim}</div>
	            <div class="skill-dots">
	              <#assign filledDots = 0>
	              <#if skill?index < 2>
	                <#assign filledDots = 5>
	              <#elseif skill?index < 4>
	                <#assign filledDots = 3>
	              <#else>
	                <#assign filledDots = 4>
	              </#if>
	
	              
	              <#list 1..filledDots as i>
	                <span class="dot filled"></span>
	              </#list>
	              <#list 1..(6 - filledDots) as j>
	                <span class="dot"></span>
	              </#list>
	            </div>
	          </div>
	        </#if>
	      </#list>
	    </div>
	  </div>
	</#if>


    

	<#if achievements?? && achievements?size gt 0> 

		 <div class="section">
		 
		    <div class="section-title">ACHIEVEMENTS</div>
		    <ul class="bullets">
		    <#list achievements as achieve>
				<#if achieve.achievementsName?? && achieve.achievementsName?has_content>
		             
		             <li>${achieve.achievementsName} 
		             
		                  <#if achieve.achievementsDate?? && achieve.achievementsDate?has_content>
						                  - ${extractYear(achieve.achievementsDate)}  
						    </#if> 
		             </li>
		        </#if>
			</#list>    
 		    </ul>
		 </div>
		  
	</#if> 	 
	
	<#if certificates?? && certificates?size gt 0> 
		 <div class="section">
		    <div class="section-title">CERTIFICATES</div>
		    <ul class="bullets">
		    <#list certificates as certi>
				<#if certi.courseName?? && certi.courseName?has_content>
		             <li>${certi.courseName} 
		                  <#if certi.courseStartDate?? && certi.courseStartDate?has_content>
						            ( ${certi.courseStartDate} 
 								          <#if  certi.courseEndDate?? && certi.courseEndDate?has_content>
		                                        - ${extractYear(certi.courseEndDate)} )
		                                                <#else>
		                                )
		                    </#if>
 						 </#if> 
		             </li>
		        </#if>
			</#list>    
 		    </ul>
		 </div>
 	</#if> 
		  
     <#if competencies?? && competencies?trim?length gt 0> 
		  <div class="section">
		    <div class="section-title">CORE COMPETENCIES</div>
		    <div class="tags">
			    <#list competencies?split(",") as comp>
			    	<#if comp?? && comp?has_content>
 			           <span class="tag"> ${comp?trim}</span>
			        </#if>
			    </#list> 
		    </div>
		  </div>
		  
    </#if> 		  
		  
    <#if softSkills?? && softSkills?trim?length gt 0> 
		  <div class="section">
		    <div class="section-title">SOFT SKILLS</div>
		    <div class="tags">
			    <#list softSkills?split(",") as skill>
			    	<#if skill?? && skill?has_content>
 			           <span class="tag"> ${skill?trim}</span>
			        </#if>
			    </#list> 
		    </div>
		  </div>
		  
    </#if> 
</div>  



</body>
</html>
