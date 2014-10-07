Backbone.View.prototype.close = function () {
    this.undelegateEvents();
};

<#list classDefinitions as classDefinition>
var ${classDefinition.className}CollectionView = Backbone.CleanupView.extend({
    el: '.contentarea',
    render: function () {
        var self = this;// Saving the scope object
        var _${classDefinition.className?uncap_first}Collection = new ${classDefinition.className}Collection();
        _${classDefinition.className?uncap_first}Collection.fetch({
            success: function (data) {
                var _${classDefinition.className?uncap_first}Template = renderTemplate('${classDefinition.className?lower_case}-list-template',{ ${classDefinition.className?uncap_first}Collection: data.models });
                self.$el.html(_${classDefinition.className?uncap_first}Template);
            }
        });
    }
});

var ${classDefinition.className}DetailsView = Backbone.CleanupView.extend({
	el: '.contentarea',
	events: {
        'click #create${classDefinition.className}': 'create${classDefinition.className}',
        'click #update${classDefinition.className}': 'update${classDefinition.className}',
        'click #closeDetails': 'closeDetails'
        
    }, 
	render: function (${classDefinition.className?uncap_first}Id) {
		var ${classDefinition.className?uncap_first}Details=null;
		if (${classDefinition.className?uncap_first}Id) {
			var self = this;
		 
			// ${classDefinition.className} update. We need to fetch ${classDefinition.className?uncap_first} details from server and
			// render the template.
			var _${classDefinition.className?uncap_first}Model = new ${classDefinition.className}Model({ id: ${classDefinition.className?uncap_first}Id });
	   
			_${classDefinition.className?uncap_first}Model.fetch({
				success: function (data) {
					var _${classDefinition.className?uncap_first}DetailTemplate = renderTemplate('${classDefinition.className?lower_case}-details-template', { ${classDefinition.className?uncap_first}: data });
					self.$el.html(_${classDefinition.className?uncap_first}DetailTemplate);
				}
			});
		}
		else
		{
			// ${classDefinition.className} is created
			var _${classDefinition.className?uncap_first}DetailTemplate = renderTemplate('${classDefinition.className?lower_case}-details-template', { ${classDefinition.className?uncap_first}: null });
			this.$el.html(_${classDefinition.className?uncap_first}DetailTemplate);
		}
	},
	create${classDefinition.className}: function () {

		// Create a ${classDefinition.className?uncap_first} model to fill the form details
		var model = new ${classDefinition.className}Model({
		
			<#assign columnData = classDefinition.columns>
			<#list classDefinition.columns as columnData>
				<#if columnData.fieldIdentifier != "id">
				${columnData.fieldIdentifier}: $('.${classDefinition.className?uncap_first}-${columnData.fieldIdentifier}').val() <#if columnData_has_next>,</#if>
				</#if>
			</#list>
		});

		model.save({}, {
			success: function () {
				window.App.appRouter.navigate('${classDefinition.className?uncap_first}/list', { trigger: true });// Navigate back to listing page
			}
		});
	},
	update${classDefinition.className}: function () {
		// Create a ${classDefinition.className?uncap_first} model to fill the form details
		var model = new ${classDefinition.className}Model({
			
			<#list classDefinition.columns as columnData>
				${columnData.fieldIdentifier}: $('.${classDefinition.className?uncap_first}-${columnData.fieldIdentifier}').val() <#if columnData_has_next>,</#if>
			</#list>
		});
		model.save({}, {
			success: function () {
				window.App.appRouter.navigate('${classDefinition.className?uncap_first}/list', { trigger: true });// Navigate back to listing page
			}
		});
	},
	closeDetails: function () {
		window.App.appRouter.navigate('${classDefinition.className?uncap_first}/list', { trigger: true });// Navigate back to listing page
	}
});
</#list>