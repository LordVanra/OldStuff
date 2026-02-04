function exampleFullName=getAdditionalExampleFullName(exampleName,spkg)
% Copyright 2018 The MathWorks Inc.

% This function calls internal spkg methods to get the full path of an
% example displayed in the app listbox
%
if(~isempty(exampleName))
    if strcmp(spkg,'cortex')
        exampleFullName = ['vexarmcortex_', exampleName, '.slx'];
    elseif strcmp(spkg, 'roboticsPlayground')
        rp_dir=fileparts(which('RoboticsPlayground'));
        exampleFullName = ['RP_', exampleName ,'.slx'];
    elseif strcmp(spkg, 'vexv5')
        exampleFullName = ['vexv5_', exampleName, '.slx'];
    end
else
    exampleFullName = '';
end


end