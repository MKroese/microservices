/*
YUI 3.4.1 (build 4118)
Copyright 2011 Yahoo! Inc. All rights reserved.
Licensed under the BSD License.
http://yuilibrary.com/license/
*/
YUI.add("arraysort",function(c){var b=c.Lang,a=b.isValue,d=b.isString;c.ArraySort={compare:function(f,e,g){if(!a(f)){if(!a(e)){return 0;}else{return 1;}}else{if(!a(e)){return -1;}}if(d(f)){f=f.toLowerCase();}if(d(e)){e=e.toLowerCase();}if(f<e){return(g)?1:-1;}else{if(f>e){return(g)?-1:1;}else{return 0;}}}};},"3.4.1",{requires:["yui-base"]});