function newModelHandle = createNewModel(spkg)
% Copyright 2018 The MathWorks Inc.
% Creates a new model that is preconfigured for the support
% package when the user clicks on the Create New Model button
waitbarHandle = waitbar(0,'Creating New Model...');
% create an empty variable called "cs" in order to make sure
% MATLAB does not think we are using the function CS from the
% system identification Toolbox.
cs = [];

% load the configset already setup for this target
if strcmp(spkg,'cortex')
    configSetMatFileName='vexarmcortex_ConfigSet.mat';
else
    configSetMatFileName='vexv5_ConfigSet.mat';
end

load(fullfile(replace(fileparts(mfilename('fullpath')),'+vexapp',''), configSetMatFileName), '-mat', 'cs');
waitbar(0.1, waitbarHandle,'Creating New Model...');
% create a new system in memory
newModelHandle = new_system;
waitbar(0.2, waitbarHandle,'Creating New Model...');
% attach the configset to the new system
attachConfigSet(newModelHandle, cs, true);
waitbar(0.4, waitbarHandle,'Creating New Model...');
% set the new configset as the active configset
setActiveConfigSet(newModelHandle, cs.Name);
waitbar(0.8, waitbarHandle,'Opening New Model...');
% open the system
open_system(newModelHandle)
waitbar(1, waitbarHandle,'New Model Created');
close(waitbarHandle);
end