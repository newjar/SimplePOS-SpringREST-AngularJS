(function() {

	var services = angular.module('je-masterdata-supplier-service', [ 'ngResource' ]);

	
	services.factory('SupplierListFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/supplier', {}, {
			query : {
				method : 'GET',
				isArray : false
			}
		});

	} ]);
    
        services.factory('SupplierCreateFactory', [ '$resource', function($resource) {

                    return $resource('service/masterdata/supplier', {}, {
                            create : {
                                    method : 'POST'
                            }
                    });

        } ]);
	
	services.factory('SupplierEditFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/supplier/:kode', {}, {
			show : {
				method : 'GET',
				params: {kode: '@kode'}
			},
			update : {
				method : 'PUT',
				params :{kode: '@kode'}
			}
		});

	} ]);
    
    
        services.factory('SupplierDeleteFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/supplier/:kode', {}, {
			delete : {
				method : 'DELETE',
				params :{kode: '@kode'}
			}
		});

	} ]);
	
})();