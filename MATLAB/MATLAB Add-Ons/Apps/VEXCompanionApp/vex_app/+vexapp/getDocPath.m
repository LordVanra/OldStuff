
function path=getDocPath(spkg)

% Copyright 2018 The MathWorks Inc.

% This function gets the location of the main html with the support package
% documentation it uses internal codertaret methods for future
% compatibility

if strcmp(spkg,'cortex')
    path=fullfile(codertarget.internal.vexarmcortex.getDocRoot, 'index.html');
elseif strcmp(spkg, 'roboticsPlayground')
    rp_dir=fileparts(which('RoboticsPlayground'));
    path=fullfile(replace(rp_dir,'lib','doc'),'html','GettingStarted.html');
else
    path=fullfile(codertarget.internal.vexv5.getDocRoot, 'index.html');
end

end