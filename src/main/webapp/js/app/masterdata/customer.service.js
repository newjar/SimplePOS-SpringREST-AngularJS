(function() {

	var services = angular.module('je-masterdata-customer-service', [ 'ngResource' ]);

	
	services.factory('CustomerListFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/customer', {}, {
			query : {
				method : 'GET',
				isArray : false
			}
		});

	} ]);
    
        services.factory('CustomerCreateFactory', [ '$resource', function($resource) {

                    return $resource('service/masterdata/customer', {}, {
                            create : {
                                    method : 'POST'
                            }
                    });

        } ]);
	
	services.factory('CustomerEditFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/customer/:kode', {}, {
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
    
    
        services.factory('CustomerDeleteFactory', [ '$resource', function($resource) {

		return $resource('service/masterdata/customer/:kode', {}, {
			delete : {
				method : 'DELETE',
				params :{kode: '@kode'}
			}
		});

	} ]);
	
})();