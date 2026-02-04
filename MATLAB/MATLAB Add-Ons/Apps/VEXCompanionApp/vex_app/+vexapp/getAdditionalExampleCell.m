function exampleCell=getAdditionalExampleCell(spkg)

% Copyright 2018 The MathWorks Inc.

% This function generates a list of examples to be used in the app listbox
% It extracts all the available exmaples from the support package examples
% folder

info = matlabshared.supportpkg.getInstalled;
vexapppath=fileparts(which('VEXCompanionApp'));

if (strcmp(spkg,'cortex'))
    isSP_installed=any(arrayfun(@(sp)strcmp(sp.Name, 'Simulink Coder Support Package for ARM Cortex-based VEX Microcontroller'), info));
    if isSP_installed
        fileNames = what(fullfile(codertarget.vexarmcortex.internal.getSpPkgRootDir, 'vexarmcortex_examples'));
        exampleNames=cellfun(@(names) replace(names,'vexarmcortex_',''),fileNames.slx','UniformOutput',false);
        exampleNames=cellfun(@(names) replace(names,'.slx',''),exampleNames,'UniformOutput',false);
        exampleCell = exampleNames;
        % Get additional examples from app
        fileNames = what(fullfile(vexapppath,'vex_app','vexcortex_models'));
        exampleNames=cellfun(@(names) replace(names,'vexarmcortex_',''),fileNames.slx','UniformOutput',false);
        exampleNames=cellfun(@(names) replace(names,'.slx',''),exampleNames,'UniformOutput',false);
        exampleCell=[exampleCell exampleNames];
    else
        exampleCell = {'No files available!'};
    end
elseif (strcmp(spkg,'vexv5'))
    % Fill in V5 examples cell when path available
     isSP_installed=any(arrayfun(@(sp)strcmp(sp.Name, 'Simulink Coder Support Package for VEX EDR V5 Robot Brain'), info));
    if isSP_installed
        fileNames = what(fullfile(codertarget.vexv5.internal.getSpPkgRootDir, 'vexv5examples'));
        exampleNames=cellfun(@(names) replace(names,'vexv5_',''),fileNames.slx','UniformOutput',false);
        exampleNames=cellfun(@(names) replace(names,'.slx',''),exampleNames,'UniformOutput',false);
        exampleCell = exampleNames;
        % Get additional examples from app
        fileNames = what(fullfile(vexapppath,'vex_app','vexv5_models'));
        exampleNames=cellfun(@(names) replace(names,'vexv5_',''),fileNames.slx','UniformOutput',false);
        exampleNames=cellfun(@(names) replace(names,'.slx',''),exampleNames,'UniformOutput',false);
        exampleCell=[exampleCell exampleNames];
    else
        exampleCell = {'No files available!'};
    end
elseif (strcmp(spkg,'roboticsPlayground'))
    tb = matlab.addons.toolbox.installedToolboxes;
    if ~isempty(find(arrayfun(@(n)strcmp(tb(n).Name, 'Robotics Playground'), 1:numel(tb)),1))
        rp_dir=fileparts(which('RoboticsPlayground'));
        fileNames = what(fullfile(replace(rp_dir,'lib','examples'),'models'));
        exampleNames=cellfun(@(names) replace(names,'RP_',''),fileNames.slx','UniformOutput',false);
        exampleNames=cellfun(@(names) replace(names,'.slx',''),exampleNames,'UniformOutput',false);
        exampleCell = exampleNames;
    else
        exampleCell = {'No files available!'};
    end
end

end