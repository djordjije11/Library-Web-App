import { createElement, useState } from "react";
import AuthClaims from "../../../models/authentication/claims/AuthClaims";
import { useAppDispatch, useAppSelector } from "../../../store/config/hooks";
import {
  Avatar,
  Button,
  Menu,
  MenuHandler,
  MenuItem,
  MenuList,
  Typography,
} from "@material-tailwind/react";
import userIcon from "../../../images/user-icon.png";
import {
  BuildingLibraryIcon,
  ChevronDownIcon,
  IdentificationIcon,
  PowerIcon,
} from "@heroicons/react/24/outline";
import { logoutThunk } from "../../../store/authentication/authThunks";

export default function ProfileMenu() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const authClaims: AuthClaims = useAppSelector(
    (state) => state.auth.authClaims
  );
  const dispatch = useAppDispatch();

  return (
    <Menu open={isMenuOpen} handler={setIsMenuOpen} placement="bottom-end">
      <MenuHandler>
        <Button
          variant="text"
          color="blue-gray"
          className="flex items-center gap-1 rounded-full py-0.5 pr-2 pl-0.5 lg:ml-auto"
        >
          <Avatar
            variant="circular"
            size="sm"
            alt={authClaims.employeeClaim.idCardNumber}
            className="border border-gray-700 p-0.5"
            src={userIcon}
          />
          <ChevronDownIcon
            strokeWidth={2.5}
            className={`h-3 w-3 transition-transform ${
              isMenuOpen ? "rotate-180" : ""
            }`}
          />
        </Button>
      </MenuHandler>
      <MenuList className="p-1">
        <MenuItem
          key={1}
          className="flex items-center gap-2 rounded pointer-events-none"
        >
          {createElement(IdentificationIcon, {
            className: "h-4 w-4",
            strokeWidth: 2,
          })}
          <Typography
            as="span"
            variant="small"
            className="font-normal"
            color="inherit"
          >
            <span>
              {authClaims.employeeClaim.firstname}{" "}
              {authClaims.employeeClaim.lastname}
              {", ID: "}
              {authClaims.employeeClaim.idCardNumber}
            </span>
          </Typography>
        </MenuItem>
        <MenuItem
          key={2}
          className="flex items-center gap-2 rounded pointer-events-none"
        >
          {createElement(BuildingLibraryIcon, {
            className: "h-4 w-4",
            strokeWidth: 2,
          })}
          <Typography
            as="span"
            variant="small"
            className="font-normal"
            color="inherit"
          >
            <span>
              {authClaims.buildingClaim.street}, {authClaims.buildingClaim.city}
            </span>
          </Typography>
        </MenuItem>
        <MenuItem
          key={3}
          className="flex items-center gap-2 rounded hover:bg-red-500/10 focus:bg-red-500/10 active:bg-red-500/10"
          onClick={() => dispatch(logoutThunk())}
        >
          {createElement(PowerIcon, {
            className: "h-4 w-4 text-red-500",
            strokeWidth: 2,
          })}
          <Typography
            as="span"
            variant="small"
            className="font-normal"
            color="red"
          >
            <span>Log out</span>
          </Typography>
        </MenuItem>
      </MenuList>
    </Menu>
  );
}
