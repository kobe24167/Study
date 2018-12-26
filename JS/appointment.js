//查找治疗师
var findDoctor=function(){
    $.post(path+"/personnel/findDoctor",{
        },function(data){
            $('#city').empty();
            var str = "";
            //var 
            if(data!=null&&data.length>0){
                for(var i=0 ; i<data.length ; i++){
                    str += '<option data-icon="&spades;" value="'+data[i].personnelPhone+'" id="'+data[i].personnelId+'">'+data[i].personnelName+'</option>'
                }
                $('#city').append('<option value="0">请选择</option>'+str);
            }
        });
}

$(document).ready(function(){
	//选择治疗师
    findDoctor();
    
});