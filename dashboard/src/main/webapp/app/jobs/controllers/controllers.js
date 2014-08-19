'use strict';


/**
 * SchedulerJob
 */
var SchedulerJobStatsController = function($http, $scope, $resource,
		$routeParams, $timeout, SessionService) {
	var spoolerId = $routeParams.spoolerId;
	var jobName = $routeParams.job;
	$http({
		url : '/jobStats',
		method : 'GET',
		params : {
			spoolerId : spoolerId,
			jobName : jobName
		}
	}).success(function(data) {
		$scope.job = data;
		$scope.chartStatsSchedulerValue = data.workingTime;
		SessionService.set("jobStats", data);
	});
	
	$scope.xAxisTickFormatFunction = function() {
		return function(d) {
			var xLabel = new Date(d);
			return d3.time.format('%y/%m/%d')(xLabel);
		};
	};

	$scope.yAxisTickFormatFunction = function() {
		return function(d) {
			return d3.format('d')(d);
		};
	};

	$scope.xFunction = function() {
		return function(d) {
			return d[0];
		};
	};

	$scope.yFunction = function() {
		return function(d) {
			return d[1];
		};
	};

	$scope.yBarFunction = function() {
		return function(d) {
			return d[1] / 1000;
		};
	};
};

var TimepickerDemoCtrl = function ($scope) {
	  $scope.mytime = new Date();

	  $scope.hstep = 1;
	  $scope.mstep = 15;
	  $scope.mstep = 20;
	  $scope.options = {
	    hstep: [1, 2, 3],
	    mstep: [1, 5, 10, 15, 25, 30]
	  };

	  $scope.ismeridian = false;
	  $scope.showSeconds = true ;
	  
	  $scope.toggleMode = function() {
	    $scope.ismeridian = ! $scope.ismeridian;
	  };

	  $scope.update = function() {
	    var d = new Date();
	    d.setHours( 14 );
	    d.setMinutes( 0 );
	    d.setSeconds( 0 );
	    $scope.mytime = d;
	  };

	  $scope.changed = function () {
	    console.log('Time changed to: ' + $scope.mytime);
	  };

	  $scope.clear = function() {
	    $scope.mytime = null;
	  };
	};
	
	
	

var ListSchedulerJobController = function($timeout, $scope, $resource,
		ngTableParams) {
	var Api = $resource('/jobs');
	$scope.tableParams = new ngTableParams({
		page : 1,
		count : 10
	// sorting: { name: 'asc' }
	}, {
		total : 0,
		getData : function($defer, params) {
			Api.get(params.url(), function(data) {
				$timeout(function() {
					params.total(data.totalElements);
					$scope.totalPages = data.totalPages;
					$defer.resolve(data.result);
				}, 500);

			});
		}
	});
};



//added by fourat 
/**
 * new Job controller 
 */
var JobEditorController = function( $scope,$http) {
	 $scope.xml ='' ;
	$scope.job = {script:{content:[]}} ;
	$scope.job_name ='No_Name_Job' ; 
	
	$scope.languages=['java','schell','javascript','sql*plus','VBscript','ssh'] ;
	
	
$scope.selectedMenuItem = 'newJob'  ;
	
	$scope.selectMenuItem = function(item){
		
		$scope.selectedMenuItem = item ;
		$scope.selectedSubMenuItem = 'script' ;
		
	};
	
$scope.selectedSubMenuItem = 'script'  ;
	
	$scope.selectSubMenuItem = function(item){
		
		$scope.selectedSubMenuItem = item ;
		
		$scope.pushScriptText = function(script){
			$scope.job.script.content =[] ;
			$scope.job.script.content.push(script) ;
			
		};
		
		if(item=='xml'){
			
			$scope.pushScriptText($scope.scriptText) ;
			console.log('Sucess get here') ;
			
		$http.post(
			 '/newjob/xml',
			
	
			 JSON.stringify($scope.job) 
				
		).success(function(data) {
			console.log(data.myString ) ;
			$scope.xmlResult = data.myString ;
		});
	};
		
		$scope.pushSignal = function(signal){
			$scope.job_name.ignoreSignals.push(signal) ;
			
		} ;
		$scope.saveJob = function() {
			console.log('SUCCESS send' ) ;
			$http.post(
			 '/newjob/saveJob',
			 JSON.stringify($scope.job) 

					
			).success(function(data) {
				alert('job '+$scope.job.name+' saved successfully !') ;
				
			});	
		
		} ;
}; 

$scope.dirFileWatch  =['momo','toto'] ;

$scope.pushDirFileWatch = function(dir){

	$scope.dirFileWatch.push(dir);
	console.log($scope.dirFileWatch) ;
};






//$scope.$watch(function($scope) {
//	return $scope.selectedSubMenuItem;
//}, function(item,$scope) {
//	
//	
//
//
//	}
//);
} ;

/**
 * job chain controller
 */

var ChainEditorController = function($scope){
	
$scope.selectedMenuItem = 'newChain'  ;
	
	$scope.selectMenuItem = function(item){
		
		$scope.selectedMenuItem = item ;
		
	};
};



var OrderEditorController = function($scope){
	
$scope.selectedMenuItem = 'newOrder'  ;
	
	$scope.selectMenuItem = function(item){
		
		$scope.selectedMenuItem = item ;
		
	};
	
$scope.parameters = [] ;
$scope.pushParameter =  function(name_,value_){
	$scope.parameters.push({name:name_,value:value_});
	console.log($scope.parameters) ;
};

$scope.selectParameter = function(name_,value_){
	$scope.selectedParameter = {name:name_,value:value_} ;
	
	console.log($scope.selectedParameter) ;
} ;

$scope.selectedParameter ={name:'',value:''} ;



$scope.removeParameter = function(){
	$scope.parameters = $scope.parameters.filter(function(el){
		return el.name != $scope.selectedParameter.name ;
	});
};

$scope.selectedSubMenuItem = 'everyDay'  ;
$scope.chosenDays =[] ;
$scope.listDays = ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday'] ;
$scope.addDay = function(){
	$scope.chosenDays.push($scope.chosenDay) ;
	$scope.listDays =  $scope.listDays.filter(function(el){
		return el != $scope.chosenDay ;
	});
};

$scope.holydayOptions=['previous non holiday','next non holiday','suppress execution','ignore holiday'] ;
$scope.selectSubMenuItem = function(item){
	
	$scope.selectedSubMenuItem = item ;
};
$scope.chosenDaysInMonth =[] ;
$scope.listDaysInMonth=['1st','2nd','3rd','4th','5th','6th','7th','8th','9th','10th'
                      ,'11th','12th','13th','14th','15th','16th','17th','18th','19th',
                      '20th','21st','22nd','23rd','24th','24th','25th' ,'26th','27th'
                      ,'28th','29th','30th','31st'] ;

$scope.addDayInMonth = function(){
	$scope.chosenDaysInMonth.push($scope.chosenDayInMonth) ;
	$scope.listDaysInMonth =  $scope.listDaysInMonth.filter(function(el){
		return el != $scope.chosenDayInMonth ;
	});
};

$scope.chosenUltimoDays =[] ;
$scope.listUltimoDays=['last day','1 day','2 days','3 days','4 days','5 days','6 days','7 days','8 days','9 days'
                      ,'10 days','11 days','12 days','13 days','14 days','15 days','16 days','17 days','18 days','19 days','20 days',
                      '21 days','22 days','23 days','24 days','25 days','26 days','27 days' ,'28 days','29 days '
                      ,'30 days'] ;

$scope.addUltimoDay = function(){
	$scope.chosenUltimoDays.push($scope.chosenUltimoDay) ;
	$scope.listUltimoDays =  $scope.listUltimoDays.filter(function(el){
		return el != $scope.chosenUltimoDay ;
	});
};


$scope.today = function() {
    $scope.chosenDate = new Date();
  };
  $scope.today();

  $scope.clear = function () {
    $scope.chosenDate = null;
  };

  // Disable weekend selection
  $scope.disabled = function(date, mode) {
    return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
  };

  $scope.toggleMin = function() {
    $scope.minDate = $scope.minDate ? null : new Date();
  };
  $scope.toggleMin();

  $scope.open = function($event) {
    $event.preventDefault();
    $event.stopPropagation();

    $scope.opened = true;
  };

  $scope.dateOptions = {
    formatYear: 'yy',
    startingDay: 1
  };

  $scope.initDate = new Date('2016-15-20');
 // $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
  $scope.format = 'yyyy-MM-dd';
  
  $scope.chosenDates =[] ;
  $scope.addDate = function() {
	  $scope.chosenDates.push($scope.chosenDate);
  };
};



var StatsController = function( $scope,$http,$sce,$timeout, $resource,
		 $q, $log) {
	
	
	$scope.isDrawing = false ;
	
	 $scope.getJob = function(val) {
		    return $http.get('/stats-test/searchJob', {
		      params: {
		    	  jobName: val,
		    	  timeout : 300000
		      }
		    }).then(function(res){
		      var jobs = [];
		      jobs = res.data  ;
		      console.log(jobs) ;
		      console.log(jobs.length) ;
		      return jobs;
		    });
		  };
	$scope.showActivity = function(){
		
	
$scope.type = 'info' ;
$scope.showWarning = false ;
		 $scope.canvas2 = document.getElementById('chart2');
			$scope.ctx2 = $scope.canvas2.getContext('2d');
			 $scope.myLineChart2 = new Chart($scope.ctx2);
			 $scope.drawClicked = true ;
			 $scope.isDrawing = true ;
 $http.post(
		 '/stats-test/activities',
		
		JSON.stringify($scope.field)
		)
				
		.success(function(data) {
			
			var options = {

				    ///Boolean - Whether grid lines are shown across the chart
				    scaleShowGridLines : true,

				    //String - Colour of the grid lines
				    scaleGridLineColor : "rgba(0,0,0,.05)",

				    //Number - Width of the grid lines
				    scaleGridLineWidth : 1,

				    //Boolean - Whether the line is curved between points
				    bezierCurve : true,

				    //Number - Tension of the bezier curve between points
				    bezierCurveTension : 0.1,

				    //Boolean - Whether to show a dot for each point
				    pointDot : true,

				    //Number - Radius of each point dot in pixels
				    pointDotRadius : 1,

				    //Number - Pixel width of point dot stroke
				    pointDotStrokeWidth : 0,

				    //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
				    pointHitDetectionRadius : 0,

				    //Boolean - Whether to show a stroke for datasets
				    datasetStroke : false,

				    //Number - Pixel width of dataset stroke
				    datasetStrokeWidth : 0,

				    //Boolean - Whether to fill the dataset with a colour
				    datasetFill : true,

				    //String - A legend template
				    legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>",
				    scaleStepWidth : 10
				    

			
			
			} ;
			

		
	        //$scope.ctx2.clearRect(0, 0, $scope.canvas2.width, $scope.canvas2.height);

			for (var key in Chart.instances){
				
				Chart.helpers.clear($scope.myLineChart2) ; 
			Chart.helpers.unbindEvents(Chart.instances[key],Chart.instances[key].events) ;
			delete Chart.instances[Chart.instances[key].id] ;
				
			}
			$scope.isDrawing = false ;
			//$scope.myLineChart.noConflict() ;
			$scope.myLineChart2.Line(data,options) ;
					} );
		
		
		
	};
	
	 $scope.canvas = document.getElementById('myChart');
	$scope.ctx = $scope.canvas.getContext('2d');
	 $scope.myLineChart = new Chart($scope.ctx);

	$scope.draw = function(){
		$scope.isDrawing = true ;
		$http.post(
				 '/stats-test/chosenJob2',
				
				 JSON.stringify($scope.singleField)
						
				).success(function(data) {
					
					var options = {
					    scaleShowGridLines : true,
					    scaleGridLineColor : "rgba(0,0,0,.05)",
					    scaleGridLineWidth : 1,
					    bezierCurve : true,
					    bezierCurveTension : 0.4,
					    pointDot : true,
					    pointDotRadius : 4,
					    pointDotStrokeWidth : 1,
					    pointHitDetectionRadius : 5,
					    datasetStroke : true,
					    datasetStrokeWidth : 2,
					    datasetFill : true,
					    legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>",
					    tooltipTemplate: "<%if (label){%> start time :<%= label%> <%}%>  duration : <%= Math.floor(value/3600) %>:<%= Math.floor((value%3600)/60) %>:<%= Math.floor(value%3600)%60 %>",
					    scaleLabel : " <%= Math.floor(value/3600) %>:<%= Math.floor((value%3600)/60) %>:<%= Math.floor(value%3600)%60 %>" 
							
					} ;
					
					for (var key in Chart.instances){
						
						Chart.helpers.clear($scope.myLineChart) ; 
					Chart.helpers.unbindEvents(Chart.instances[key],Chart.instances[key].events) ;
					delete Chart.instances[Chart.instances[key].id] ;
						
					}
					$scope.isDrawing = false ;
					$scope.myLineChart.Line(data,options) ;
					
					
					
				}).error(function(date){alert('error!') ;});
	};
	
	
	
	
	
	$scope.selectedMenuItem = 'singleJob' ; 
	$scope.selectMenuItem = function(item) {
		$scope.selectedMenuItem = item ;
	}  ;
	
	
		  $scope.today = function() {
		    $scope.dt = new Date();
		  };
		  $scope.today();

		  $scope.clear = function () {
		    $scope.dt = null;
		  };

		

		  $scope.toggleMin = function() {
		    $scope.minDate = $scope.minDate ? null : new Date();
		  };
		  $scope.toggleMin();

		  $scope.open = function($event,id) {
		    $event.preventDefault();
		    $event.stopPropagation();
		 
		    switch(id){
		    case 1 :     $scope.opened1 = true; break ;
		    case 2 :	 $scope.opened2 = true; break ;
		    case 3 : 	 $scope.opened3 = true; break ;
		    case 4 :	 $scope.opened4 = true; break ;
		    case 5 :	 $scope.opened5 = true; break ;
		    case 6 :	 $scope.opened6 = true; break ;
		    case 7 :	 $scope.opened7 = true; break ;
		    case 8 :	 $scope.opened8 = true; break ;
		    case 9 :	 $scope.opened9 = true; break ;
		    case 10 :	 $scope.opened10 = true; break ;
		    }
		  };

		  $scope.dateOptions = {
		    formatYear: 'yy',
		    startingDay: 1
		  };
		  
		  $scope.getToday = function(){
			  return new Date()+"" ;
		  } ;

		  $scope.initDate = new Date('2016-15-20');
		  $scope.formats = ['yyyy-MM-dd', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
		  $scope.format = $scope.formats[0];
	
		  $scope.canvas3 = document.getElementById('chart3');
			$scope.ctx3 = $scope.canvas3.getContext('2d');
			 $scope.myLineChart3 = new ChartDouble($scope.ctx3);
			  $scope.canvas4 = document.getElementById('chart4');
				$scope.ctx4 = $scope.canvas4.getContext('2d');
				 $scope.myLineChart4 = new Chart($scope.ctx4);
			 $scope.drawExits = function() {
				
				 $scope.isDrawing = true ;
						$http.post(
								 '/stats-test/byExitCodes',
									
								 JSON.stringify($scope.exitFields)
						).success(function(data){

							var options = {

								    ///Boolean - Whether grid lines are shown across the chart
								    scaleShowGridLines : true,

								    //String - Colour of the grid lines
								    scaleGridLineColor : "rgba(0,0,0,.05)",
								    scaleStepWidth : 10 ,
								    //Number - Width of the grid lines
								    scaleGridLineWidth : 1,

								    //Boolean - Whether the line is curved between points
								    bezierCurve : true,

								    //Number - Tension of the bezier curve between points
								    bezierCurveTension : 0.1,

								    //Boolean - Whether to show a dot for each point
								    pointDot : true,

								    //Number - Radius of each point dot in pixels
								    pointDotRadius : 1,

								    //Number - Pixel width of point dot stroke
								    pointDotStrokeWidth : 0,

								    //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
								    pointHitDetectionRadius : 0,

								    //Boolean - Whether to show a stroke for datasets
								    datasetStroke : false,

								    //Number - Pixel width of dataset stroke
								    datasetStrokeWidth : 2,

								    //Boolean - Whether to fill the dataset with a colour
								    datasetFill : true,

								    //String - A legend template
								    legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>",
								    
								        showDatasetLabels : true

							
							
							} ;
							//$scope.drawClicked = false ;

						
					        //$scope.ctx2.clearRect(0, 0, $scope.canvas2.width, $scope.canvas2.height);

							
							var i ;
							$scope.retour = '<ul> ' ;
							for(i = 0 ;i<data.datasets.length;i++ ){
								$scope.retour = $scope.retour +'<li style="color : '+ data.datasets[i].strokeColor+'; font-weight : bold ;">'+data.datasets[i].label + '</li> ';
							}
							
							$scope.retour = $sce.trustAsHtml($scope.retour +"</ul>") ;
							
							console.log($scope.retour) ;
							for (var key in Chart.instances){
							
							Chart.helpers.clear($scope.myLineChart4) ; 
						Chart.helpers.unbindEvents(Chart.instances[key],Chart.instances[key].events) ;
						delete Chart.instances[Chart.instances[key].id] ;
							
						}
							$scope.isDrawing = false ;
							$scope.myLineChart4.Line(data,options) ;
							//$scope.myLineChart4.generateLegendTemplate() ;
							} );
						
						
						

						
					};
//		generateLegends = function(data){
//			var i , retour ; 
//			retour = ' ' ;
//			for(i = 0 ;i<data.datasets.length;i++ ){
//				retour = retour + data.datasets[i].label + ' ' + data.datasets[i].fillColor +'\n' ;
//			}
//			
//			return retour ;
//		};
//			
$scope.drawErrors = function(){
	$scope.isDrawing = true ;
	$http.post(
			 '/stats-test/getErrors',
				
			 JSON.stringify($scope.errorFields)
	).success(function(data){

		var options = {

			    ///Boolean - Whether grid lines are shown across the chart
			    scaleShowGridLines : true,

			    //String - Colour of the grid lines
			    scaleGridLineColor : "rgba(0,0,0,.05)",

			    //Number - Width of the grid lines
			    scaleGridLineWidth : 1,

			    //Boolean - Whether the line is curved between points
			    bezierCurve : true,

			    //Number - Tension of the bezier curve between points
			    bezierCurveTension : 0.1,

			    //Boolean - Whether to show a dot for each point
			    pointDot : true,

			    //Number - Radius of each point dot in pixels
			    pointDotRadius : 1,

			    //Number - Pixel width of point dot stroke
			    pointDotStrokeWidth : 0,

			    //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
			    pointHitDetectionRadius : 0,

			    //Boolean - Whether to show a stroke for datasets
			    datasetStroke : false,

			    //Number - Pixel width of dataset stroke
			    datasetStrokeWidth : 0,

			    //Boolean - Whether to fill the dataset with a colour
			    datasetFill : true,

			    //String - A legend template
			    legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>",
			 //   scaleLabel : "<%if (value>1000){%> <%=(value/1000)+'k'%> <%}else{%> <%=value%><%}%>",
			    Y1_scaleOverride:true,Y2_scaleOverride:true,Y1_scaleStepWidth:1000,Y1_scaleStartValue:0,Y2_scaleStepWidth:40,Y2_scaleStartValue:0
		
		
		} ;
	

	

		
		console.log(data) ;
//		for (var key in Chart.instances){
//			
//			ChartDouble.helpers.clear($scope.myLineChart3) ; 
//		ChartDouble.helpers.unbindEvents(ChartDouble.instances[key],ChartDouble.instances[key].events) ;
//		delete ChartDouble.instances[ChartDouble.instances[key].id] ;
//			
//		}
		$scope.isDrawing = false ;
		$scope.myLineChart3.LineDoubleY(data,options) ;
				} );
	
	

	
};

$scope.selectedTown='Toulouse' ;

$scope.changeDataBase = function(town){
	$scope.selectedTown=town ;
	$http.post(
			'/stats-test/switchDB',
			town
	).success(function(data){
		console.log("data base changed !!") ;
} );
	
} ;
$scope.jobClass = function(job){
	if (job.exit == 0) {
		return 'danger' ;
	}else{
		return 'info' ;
	}
} ;
$scope.getTops = function(){
	$scope.topTenJobs=[] ;
	$http.post(
			'/stats-test/getTops',
			JSON.stringify({ 
				top : $scope.top,
				start : $scope.topFields.startDate,
			 end : $scope.topFields.endDate,})
	).success(function(data){
		
		$scope.topTenJobs = data ;
		console.log(data) ;
} );
	
	

	
};
$scope.getNbJobsBetween2Date = function(startDate, endDate, jobInError) {
	$http({
		url : '/nbJobsBetween2Date',
		method : 'GET',
		params : {
			startDate : startDate,
			endDate : endDate,
			jobInError : jobInError
		}
	}).success(function(data) {
		$scope.nbJobsBetween2Date = data;
	});
};

$scope.getLongRunningJobsBetween2Date = function(startDate, endDate,
		jobInError) {
	$http({
		url : '/longRunningJobsBetween2Date',
		method : 'GET',
		params : {
			startDate : startDate,
			endDate : endDate
		}
	}).success(function(data) {
		$scope.longRunningJobsBetween2Date = data;
	});
};

$scope.xAxisTickFormatFunction = function() {
	return function(d) {
		return d3.time.format('%Y/%m/%d')(new Date(d));
	};
};

$scope.yAxisTickFormatFunction = function() {
	return function(d) {
		return d3.format('d')(d);
	};
};

$scope.xFunction = function() {
	return function(d) {
		return d[0];
	};
};

$scope.yFunction = function() {
	return function(d) {
		return d[1];
	};
};

$scope.yBarFunction = function() {
	return function(d) {
		return d[1] / 1000;
	};
};


} ;



//
/**
 * SchedulerHistory
 */
var SchedulerHistoryController = function($scope, $http, $routeParams) {
	$scope.back = function() {
		window.history.back();
	};
	var schedulerHistoryId = $routeParams.schedulerHistoryId;
	$http({
		url : '/schedulerHistory',
		method : 'GET',
		params : {
			id : schedulerHistoryId
		}
	}).success(function(data) {
		$scope.job = data;
	});

};

var ListSchedulerHistoryController = function($timeout, $scope, $http,
		$resource, ngTableParams, SessionService) {
	var ApiSchedulerHistories = $resource('/schedulerHistories');
	// Load filters in select option
	$http({
		url : '/filters',
		method : 'GET'
	}).success(function(data) {
		$scope.filters = data;
	});

	$scope.tableParams = new ngTableParams({
		page : 1,
		count : 10
	// sorting: { jobName: 'asc' }
	}, {
		total : 0,
		getData : function($defer, params) {
			ApiSchedulerHistories.get(params.url(), function(data) {
				$timeout(function() {
					// set total for recalc pagination
					params.total(data.totalElements);
					$scope.totalPages = data.totalPages;
					$defer.resolve(data.result);
					SessionService.set("jobs", data.result);
				}, 500);

			});
		}
	});

	// Watch option
	$scope.$watch('filterSelect', function(newValue, oldValue) {
		if (newValue != oldValue) {
			$http({
				url : '/filter',
				method : 'GET',
				params : {
					name : newValue
				}
			}).success(function(data) {
				if (data) {
					$scope.tableParams.filter({});
					for (var i = 0; i < data.fields.length; i++) {
						var key = Object.keys(data.fields[i])[0].toString();
						var val = data.fields[i][key];
						$scope.tableParams.filter()[key] = val.toString();
					}
				}
			});
		}
		;
	});
};

var ChartSchedulerHistoryController = function($timeout, $scope, $resource,
		$http, $q, $log) {
	$scope.getNbJobsBetween2Date = function(startDate, endDate, jobInError) {
		$http({
			url : '/nbJobsBetween2Date',
			method : 'GET',
			params : {
				startDate : startDate,
				endDate : endDate,
				jobInError : jobInError
			}
		}).success(function(data) {
			$scope.nbJobsBetween2Date = data;
		});
	};

	$scope.getLongRunningJobsBetween2Date = function(startDate, endDate,
			jobInError) {
		$http({
			url : '/longRunningJobsBetween2Date',
			method : 'GET',
			params : {
				startDate : startDate,
				endDate : endDate
			}
		}).success(function(data) {
			$scope.longRunningJobsBetween2Date = data;
		});
	};

	$scope.xAxisTickFormatFunction = function() {
		return function(d) {
			return d3.time.format('%Y/%m/%d')(new Date(d));
		};
	};

	$scope.yAxisTickFormatFunction = function() {
		return function(d) {
			return d3.format('d')(d);
		};
	};

	$scope.xFunction = function() {
		return function(d) {
			return d[0];
		};
	};

	$scope.yFunction = function() {
		return function(d) {
			return d[1];
		};
	};

	$scope.yBarFunction = function() {
		return function(d) {
			return d[1] / 1000;
		};
	};
};

var DatepickerDemoCtrl = function ($scope) {
	  $scope.today = function() {
	    $scope.dt = new Date();
	  };
	  $scope.today();

	  $scope.clear = function () {
	    $scope.dt = null;
	  };

	  // Disable weekend selection
	  $scope.disabled = function(date, mode) {
	    return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
	  };

	  $scope.toggleMin = function() {
	    $scope.minDate = $scope.minDate ? null : new Date();
	  };
	  $scope.toggleMin();

	  $scope.open = function($event) {
	    $event.preventDefault();
	    $event.stopPropagation();

	    $scope.opened = true;
	  };

	  $scope.dateOptions = {
	    formatYear: 'yy',
	    startingDay: 1
	  };

	  $scope.initDate = new Date('2016-15-20');
	  $scope.formats = ['yyyy-MM-dd hh:mm:ss', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
	  $scope.format = $scope.formats[0];
	};
	
	
	
	var ModalDemoCtrl = function ($scope, $modal, $log) {

		  $scope.items = ['item1', 'item2', 'item3'];

		  $scope.openModal = function (size) {
			 console.log("zrgnrzg") ;
		    var modalInstance = $modal.open({
		      templateUrl: 'myModalContent.html',
		      controller: ModalInstanceCtrl,
		      size: size,
		      resolve: {
		        items: function () {
		          return $scope.items;
		        }
		      }
		    });

		    modalInstance.result.then(function (selectedItem) {
		      $scope.selected = selectedItem;
		    }, function () {
		      $log.info('Modal dismissed at: ' + new Date());
		    });
		  };
		};

		// Please note that $modalInstance represents a modal window (instance) dependency.
		// It is not the same as the $modal service used above.

		var ModalInstanceCtrl = function ($scope, $modalInstance, items) {

		  $scope.items = items;
		  $scope.selected = {
		    item: $scope.items[0]
		  };

		  $scope.ok = function () {
		    $modalInstance.close($scope.selected.item);
		  };

		  $scope.cancel = function () {
		    $modalInstance.dismiss('cancel');
		  };
		};