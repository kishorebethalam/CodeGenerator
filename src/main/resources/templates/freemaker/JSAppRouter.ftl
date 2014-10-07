function setupDynamicRouting() {
	
	var AppRouter = Backbone.CleanupRouter.extend({
	
		initialize: function(){
    		this.appView = window.AppView;
  		},
        routes: {
        	'': 'home',//Default route, when no hash tag is available
        	<#list classDefinitions as classDefinition>
        	'${classDefinition.className?uncap_first}/list': 'list${classDefinition.className}',
        	'${classDefinition.className?uncap_first}/create': 'create${classDefinition.className}',
        	'${classDefinition.className?uncap_first}/edit/:id': 'edit${classDefinition.className}',
        	'${classDefinition.className?uncap_first}/delete/:id': 'delete${classDefinition.className}'<#if classDefinition_has_next>,</#if>        		        		        		
        	</#list>
        }
    });

    // Instantiate the router
     var route = new AppRouter();
		 // When hash tag has localhost# register the below route
	route.on('route:home', function () {
		selectFirstTab();	
	});

    <#list classDefinitions as classDefinition>
    
    route.on('route:list${classDefinition.className}', function () {
		var listView = new ${classDefinition.className}CollectionView();
		listView.render();
		this.markCurrentView(listView);		
	});
	route.on('route:create${classDefinition.className}', function () {
		var detailsView = new ${classDefinition.className}DetailsView();
    	detailsView.render();
    	this.markCurrentView(detailsView);
    });
	route.on('route:edit${classDefinition.className}', function (id) {
		var detailsView = new ${classDefinition.className}DetailsView();
	   	detailsView.render(id);
    	this.markCurrentView(detailsView);	   	
	});
	route.on('route:delete${classDefinition.className}', function (id) {
		var model = new ${classDefinition.className}Model({'id':id});
		model.destroy();
		route.navigate('${classDefinition.className?uncap_first}/list', { trigger: true });// Navigate back to listing page
	});
    </#list>    	


    // Start Backbone history a necessary step for bookmarkable URL's
    Backbone.history.start();
    
    App.appRouter = route;
};
function populateTabs(tabsContainerSelector, popoulateTabsCallback){
	
	<#list classDefinitions as classDefinition>
	populateTab(tabsContainerSelector, '${classDefinition.className}', function(){
		App.appRouter.navigate('${classDefinition.className?uncap_first}/list', {trigger:true});
	});
	</#list>

 };